package com.example.paymentservice.PaymentGateways;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayChoser {
    private RazorPayGateway razorPayGateway;
    private StripeGateway stripeGateway;
    public PaymentGatewayChoser(StripeGateway stripeGateway, RazorPayGateway razorPayGateway){
        this.stripeGateway = stripeGateway;
        this.razorPayGateway = razorPayGateway;
    }
    public PaymentGateway getBestPaymentgateway(){
        /*int randomInt = new Random().nextInt();

        if (randomInt % 2 == 0) {
            return razorpayPaymentGateway;
        }

        return stripePaymentGateway;*/
        return stripeGateway;
    }
}
