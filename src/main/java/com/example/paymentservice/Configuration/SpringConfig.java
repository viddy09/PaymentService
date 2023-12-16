package com.example.paymentservice.Configuration;

import com.example.paymentservice.PaymentGateways.PaymentGateway;
import com.example.paymentservice.PaymentGateways.PaymentGatewayChoser;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /*@Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;*/

    private final PaymentGatewayChoser paymentGatewayChoser;
    public SpringConfig(PaymentGatewayChoser paymentGatewayChoser){
        this.paymentGatewayChoser = paymentGatewayChoser;
    }

   /* @Bean
    public StripeClient createStripeClient() throws StripeException {
        return new StripeClient(stripeSecretKey);
    }*/

    @Bean
    public PaymentGateway createPaymentGateway(){
        return paymentGatewayChoser.getBestPaymentgateway();
    }
}
