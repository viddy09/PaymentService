package com.example.paymentservice.PaymentGateways;

import com.stripe.exception.StripeException;

public interface PaymentGateway {
    String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) throws StripeException;
}
