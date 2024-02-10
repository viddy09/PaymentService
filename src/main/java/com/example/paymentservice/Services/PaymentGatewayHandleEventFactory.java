package com.example.paymentservice.Services;

import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Repository.PaymentRepo;
import com.example.paymentservice.Services.EventHandler.HandleEvent;
import com.example.paymentservice.Services.EventHandler.RazorPayEventHandler;
import com.example.paymentservice.Services.EventHandler.StripeEventHandler;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayHandleEventFactory {

    private final PaymentRepo paymentRepo;

    public PaymentGatewayHandleEventFactory(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public HandleEvent getPaymentGatewayHandleEvent(PaymentProvider paymnetProvider){
        if (PaymentProvider.RAZORPAY.equals(paymnetProvider)){
            return new RazorPayEventHandler(paymentRepo);
        }
        else{
            return new StripeEventHandler(paymentRepo);
        }
    }
}
