package com.example.paymentservice.Services.EventHandler;

import com.example.paymentservice.DTOs.EventDTO;
import com.example.paymentservice.DTOs.RazorPayEventDTO;
import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentStatus;
import com.example.paymentservice.Repository.PaymentRepo;
import org.springframework.stereotype.Service;

@Service
public class RazorPayEventHandler implements HandleEvent {
    private PaymentRepo paymentRepo;

    public RazorPayEventHandler() {

    }
    public RazorPayEventHandler(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void handleEvent(EventDTO event) {
        RazorPayEventDTO razorPayEventDTO = (RazorPayEventDTO) event;
        Payment payment = null;
        if(razorPayEventDTO.getEvent().equals("payment.captured")){
            payment.setStatus(PaymentStatus.Successful);
        }
        else {
            payment.setStatus(PaymentStatus.Failed);
        }
        paymentRepo.save(payment);
    }
}
