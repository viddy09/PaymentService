package com.example.paymentservice.Services;

import com.example.paymentservice.PaymentGateways.PaymentGateway;
import com.example.paymentservice.PaymentGateways.PaymentGatewayChoser;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PaymentService{
    private final PaymentGateway paymentGateway;
    private final PaymentGatewayChoser paymentGatewayChoser;
    public PaymentService(PaymentGatewayChoser paymentGatewayChoser, PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
        this.paymentGatewayChoser = paymentGatewayChoser;
    }
    public String initiatePayment(String OrderId, long Contact, int amount, String email) throws StripeException{
        String link = paymentGatewayChoser.getBestPaymentgateway().generatePaymentlink(OrderId, Contact, amount, email);
        return link;
    }
}
