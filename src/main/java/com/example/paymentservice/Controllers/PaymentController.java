package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTOs.IntiatePaymentDTO;
import com.example.paymentservice.Services.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("")
    public String initiatePayment(@RequestBody IntiatePaymentDTO intiatePaymentDTO) throws StripeException, RazorpayException {
        return paymentService.initiatePayment(intiatePaymentDTO.getOrder_id(), intiatePaymentDTO.getContact(),
                intiatePaymentDTO.getAmount(), intiatePaymentDTO.getEmail());
    }
}
