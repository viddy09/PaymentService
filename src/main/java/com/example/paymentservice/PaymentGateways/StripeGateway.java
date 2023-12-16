package com.example.paymentservice.PaymentGateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.param.PaymentLinkCreateParams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Getter
@Setter
public class StripeGateway implements PaymentGateway{
    @Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;
    @Override
    public String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice("price_1ONyMySJrlJCKGv5VNmX2xlA")
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl().toString();
    }
}
