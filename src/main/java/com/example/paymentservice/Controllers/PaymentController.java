package com.example.paymentservice.Controllers;

import com.example.paymentservice.DTOs.IntiatePaymentDTO;
import com.example.paymentservice.Services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    @PostMapping("/")
    public String initiatePayment(@RequestBody IntiatePaymentDTO intiatePaymentDTO) throws StripeException {
        return paymentService.initiatePayment(intiatePaymentDTO.getOrderId(), intiatePaymentDTO.getContact(),
                intiatePaymentDTO.getAmount(), intiatePaymentDTO.getEmail());
    }
}
