package com.example.paymentservice.Configuration;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfigs {
    @Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;

    @Bean
    public StripeClient createStripeClient() throws StripeException {
        return new StripeClient(stripeSecretKey);
    }
}
