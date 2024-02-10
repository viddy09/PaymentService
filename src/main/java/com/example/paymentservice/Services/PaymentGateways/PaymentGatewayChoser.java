package com.example.paymentservice.Services.PaymentGateways;

import org.springframework.stereotype.Service;

import java.util.Random;

/*
* Random PaymentGatewayChoser
* */
@Service
public class PaymentGatewayChoser {
    private final RazorPayGateway razorPayGateway;
    private final StripeGateway stripeGateway;
    public PaymentGatewayChoser(StripeGateway stripeGateway, RazorPayGateway razorPayGateway){
        this.stripeGateway = stripeGateway;
        this.razorPayGateway = razorPayGateway;
    }
    public PaymentGateway getBestPaymentgateway(){
        Random random = new Random();
        int randomInt = random.nextInt();

        /*if (randomInt % 2 == 0) {
            return razorPayGateway;
        }*/
        return razorPayGateway;
    }
}
