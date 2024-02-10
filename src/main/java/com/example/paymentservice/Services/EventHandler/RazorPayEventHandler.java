package com.example.paymentservice.Services.EventHandler;

import com.example.paymentservice.DTOs.EventDTO;
import com.example.paymentservice.DTOs.RazorPayEventDTO;
import com.example.paymentservice.DTOs.RazorPayEventDTO.*;
import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentStatus;
import com.example.paymentservice.Repository.PaymentRepo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RazorPayEventHandler implements HandleEvent {
    private final PaymentRepo paymentRepo;
    public RazorPayEventHandler(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void handleEvent(EventDTO event) {
        RazorPayEventDTO razorPayEventDTO = (RazorPayEventDTO) event;

        Map<String, Object> notes= Optional.of(razorPayEventDTO)
                .map(RazorPayEventDTO::getPayload)
                .map(PayloadObject::getPayment)
                .map(PaymentObject::getEntity)
                .map(EntityObject::getNotes)
                        .get();
        if(!notes.containsKey("paymentId")) return;

        Long paymentId = Long.valueOf((String) notes.get("paymentId"));
        //Getting Payment Object from Payment Repository
        Optional<Payment> optionalPayment = paymentRepo.findById(paymentId);
        if(!optionalPayment.isPresent()) return;
        Payment payment = optionalPayment.get();

        boolean flag = false;
        //If Payment done successful
        if(razorPayEventDTO.getEvent().equals("payment.captured")){
            payment.setStatus(PaymentStatus.Successful);
            flag = true;
        }//If Payment done Unsuccessful
        else if(razorPayEventDTO.getEvent().equals("payment.failed")) {
            payment.setStatus(PaymentStatus.Failed);
            flag = true;
        }
        //Updating Payment Status
        if(flag) paymentRepo.save(payment);
    }
}
