package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTOs.RazorPayEventDTO;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.*;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/razorPayWebhook")
public class RazorPaymentController{
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Value(value = "${razorPayEndPointSecret}")
    private String razorPayEndPointSecret;
    public RazorPaymentController(PaymentService paymentService, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("")
    public void webhookEvents(@Nonnull @RequestBody String request,
                              @RequestHeader("X-Razorpay-Signature") String sigHead) throws RazorpayException {
        try{
            //Event Authentication through Stripe
            Utils.verifyWebhookSignature(request, sigHead, razorPayEndPointSecret);
            //Handling Event if all good
            RazorPayEventDTO razorPayEventDTO = objectMapper.readValue(request, RazorPayEventDTO.class);
            System.out.println("RazorPay Listening " + razorPayEventDTO.getEvent());
            paymentService.handleWebhookEvent(razorPayEventDTO, PaymentProvider.RAZORPAY);
        }catch (RazorpayException e){
            //Invalid Signature
            System.out.println("Invalid Signature "+e.getMessage());
        }catch (JsonParseException e) {
            //Invalid Payload
            System.out.println("Invalid Payload "+e.getMessage());
        }catch (Exception e){
            System.out.println("Something went wrong!! "+e.getMessage());
        }
    }

}
