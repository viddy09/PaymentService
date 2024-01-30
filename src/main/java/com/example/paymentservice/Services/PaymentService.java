package com.example.paymentservice.Services;

import com.example.paymentservice.DTOs.EventDTO;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Services.EventHandler.HandleEvent;
import com.example.paymentservice.Services.PaymentGateways.PaymentGateway;
import com.example.paymentservice.Services.PaymentGateways.PaymentGatewayChoser;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService{
    private final PaymentGateway paymentGateway;
    private final PaymentGatewayChoser paymentGatewayChoser;
    private final HandleEvent handleEvent;
    private final PaymentGatewayHandleEventFactory paymentGatewayHandleEventFactory;
    public PaymentService(PaymentGatewayChoser paymentGatewayChoser,
                          PaymentGateway paymentGateway,
                          HandleEvent handleEvent,
                          PaymentGatewayHandleEventFactory paymentGatewayHandleEventFactory){
        this.paymentGateway = paymentGateway;
        this.paymentGatewayChoser = paymentGatewayChoser;
        this.handleEvent = handleEvent;
        this.paymentGatewayHandleEventFactory = paymentGatewayHandleEventFactory;
    }
    public String initiatePayment(String OrderId, long Contact, int amount, String email) throws StripeException{
        String link = paymentGatewayChoser.getBestPaymentgateway().generatePaymentlink(OrderId, Contact, amount, email);
        return link;
    }

    public void handleWebhookEvent(EventDTO eventDTO, PaymentProvider paymnetProvider) {
        paymentGatewayHandleEventFactory.getPaymentGatewayHandleEvent(paymnetProvider).handleEvent(eventDTO);
    }
}
