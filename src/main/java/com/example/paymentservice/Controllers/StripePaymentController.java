package com.example.paymentservice.Controllers;

import com.stripe.model.Event;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripeWebhook")
public class StripePaymentController {
    @PostMapping
    public void stripeListening(Event event){
        System.out.println("Listening");
    }
}
