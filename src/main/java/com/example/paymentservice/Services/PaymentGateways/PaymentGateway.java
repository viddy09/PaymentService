package com.example.paymentservice.Services.PaymentGateways;

import com.stripe.exception.StripeException;

public interface PaymentGateway {
    String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) throws StripeException;
}
