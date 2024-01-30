package com.example.paymentservice.Services.PaymentGateways;

import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Repository.PaymentRepo;
import com.example.paymentservice.Utility.PaymentUtility;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Primary
@Service
@Getter
@Setter
public class StripeGateway implements PaymentGateway{

    private final PaymentRepo paymentRepo;

    private final PaymentUtility paymentUtility;

    private final StripeGatewayFacade stripeGatewayFacade;

    @Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;

    public StripeGateway(PaymentRepo paymentRepo,
                         PaymentUtility paymentUtility,
                         StripeGatewayFacade stripeGatewayFacade) {
        this.paymentRepo = paymentRepo;
        this.paymentUtility = paymentUtility;
        this.stripeGatewayFacade = stripeGatewayFacade;
    }

    @Override
    public String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        //Creating Payment Object to store
        Payment payment = null;
      /*  if(!orderId.isEmpty()){*/
            payment = paymentUtility.getPaymentRecord(orderId, amount, PaymentProvider.RAZORPAY);
            payment = paymentRepo.save(payment);
/*        }*/

        //Generate Payment Link
        //Creating Product
        Product product = stripeGatewayFacade.createProduct();
        //Creating Price
        Price price = stripeGatewayFacade.createPrice(product, amount);
        //Get Payment Link Parameter
        Map<String, Object> params = stripeGatewayFacade.getPaymentLinkParms(price, payment);

        PaymentLink paymentLink = null;
        try {
            paymentLink = PaymentLink.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return String.valueOf(Optional.of(paymentLink).map(PaymentLink::getUrl).orElse(null));
    }
}
