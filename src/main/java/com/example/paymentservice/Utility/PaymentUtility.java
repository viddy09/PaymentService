package com.example.paymentservice.Utility;

import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentMode;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Models.PaymentStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentUtility {
    public Payment getPaymentRecord(String orderId, int amount, PaymentProvider paymnetProvider){
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setStatus(PaymentStatus.Pending);
        payment.setMode(PaymentMode.UPI);
        payment.setPaymentProvider(paymnetProvider);
        payment.setAmount(BigDecimal.valueOf(amount));
        return payment;
    }
}
