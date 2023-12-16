package com.example.paymentservice.PaymentGateways;

import org.springframework.stereotype.Service;

@Service
public class RazorPayGateway implements PaymentGateway{
    @Override
    public String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) {
        return null;
    }
}
