package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTOs.RazorPayEventDTO;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Services.PaymentService;
import com.razorpay.*;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/razorPayWebhook")
public class RazorPaymentController{
    private final PaymentService paymentService;
    public RazorPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/")
    public void webhookEvents(@Nonnull @RequestBody RazorPayEventDTO razorPayEventDTO) throws RazorpayException {
        System.out.println("RazorPay Listening" + razorPayEventDTO.getEvent());
        paymentService.handleWebhookEvent(razorPayEventDTO, PaymentProvider.RAZORPAY);
    }
}
