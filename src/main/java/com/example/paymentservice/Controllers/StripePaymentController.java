package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTOs.StripeEventDTO;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.web.bind.annotation.*;

/*
* Controller handles Stripe events such as checkout completed, checkout Failed
* to confirm payment done successfully or failed.
*
* */
@RestController
@RequestMapping("/stripeWebhook")
public class StripePaymentController {
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Value(value = "${endPointSecret}")
    private String endPointSecret;
    public StripePaymentController(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/")
    public void webhookEvents(@Nonnull @RequestBody String request,
                              @RequestHeader("Stripe-Signature") String sigHeader) throws StripeException {
        Event event = null;
        try {
            event = Webhook.constructEvent(request, sigHeader, endPointSecret);
            //Event Authentication through Stripe
            StripeEventDTO stripeEventDTO = objectMapper.readValue(request,StripeEventDTO.class);
            System.out.println("Stripe Listening" + stripeEventDTO.getType());
            paymentService.handleWebhookEvent(stripeEventDTO, PaymentProvider.Stripe);
        } catch (JsonParseException e) {
            // Invalid payload
            System.out.println("Invalid payload");
            return;
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("Invalid Signature");
            /*System.out.println(e.getMessage());*/
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
