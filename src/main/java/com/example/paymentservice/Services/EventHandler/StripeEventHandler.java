package com.example.paymentservice.Services.EventHandler;

import com.example.paymentservice.DTOs.EventDTO;
import com.example.paymentservice.DTOs.StripeEventDTO;
import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentStatus;
import com.example.paymentservice.Repository.PaymentRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.param.PaymentLinkUpdateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Primary
@Service
public class StripeEventHandler implements HandleEvent {

    private final PaymentRepo paymentRepo;
    @Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
    public StripeEventHandler(PaymentRepo paymentRepo){
        this.paymentRepo = paymentRepo;
    }
    @Override
    public void handleEvent(EventDTO event) {
        StripeEventDTO stripeEventDTO = (StripeEventDTO) event;
        Map<String, String> metadata = null;
        PaymentLink paymentLink = null;
        String paymentLinkId = (String) Optional.of(stripeEventDTO)
                .map(StripeEventDTO::getData)
                .map(StripeEventDTO.CheckoutSessionData::getObject)
                .map(StripeEventDTO.CheckoutSessionObject::getPayment_link)
                .orElse(null);
        if(paymentLinkId != null){
            try{
                //Retrieving MetaData to  Update Payment Status.
                paymentLink = PaymentLink.retrieve(paymentLinkId);
                metadata = paymentLink.getMetadata();
            }
            catch (StripeException e){
                System.out.println(e.getMessage());
            }
        }
        if(metadata != null){
            if(metadata.containsKey("paymentId")){
                //Getting Payment Object from PaymentRepo
                Optional<Payment> optionalPayment = paymentRepo.findById(Long.valueOf(metadata.get("paymentId")));
                if(!optionalPayment.isPresent()){
                    return;
                }
                Payment payment = optionalPayment.get();
                boolean flag = false;
                if(stripeEventDTO.getType().equals("checkout.session.completed")){
                    flag =true;
                    payment.setStatus(PaymentStatus.Successful);
                } else if (stripeEventDTO.getType().equals("checkout.session.expired") ||
                             stripeEventDTO.getType().equals("checkout.session.async_payment_failed")){
                    payment.setStatus(PaymentStatus.Failed);
                    flag = true;
                }
                if(flag){
                    paymentRepo.save(payment);
                    PaymentLinkUpdateParams params = PaymentLinkUpdateParams.builder().setActive(false).build();
                    try {
                        paymentLink.update(params);
                    } catch (StripeException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
