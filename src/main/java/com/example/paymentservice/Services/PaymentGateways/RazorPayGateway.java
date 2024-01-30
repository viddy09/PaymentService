package com.example.paymentservice.Services.PaymentGateways;

import com.example.paymentservice.Models.Payment;
import com.example.paymentservice.Models.PaymentProvider;
import com.example.paymentservice.Repository.PaymentRepo;
import com.example.paymentservice.Utility.PaymentUtility;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RazorPayGateway implements PaymentGateway{
    private final RazorpayClient razorpayClient;
    private final PaymentRepo paymentRepo;
    private final PaymentUtility paymentUtility;
    public RazorPayGateway(RazorpayClient razorpayClient,
                           PaymentRepo paymentRepo,
                           PaymentUtility paymentUtility) {
        this.razorpayClient = razorpayClient;
        this.paymentRepo = paymentRepo;
        this.paymentUtility = paymentUtility;
    }
    @Override
    public String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) {
        Payment payment = null;
        /*if(!orderId.isEmpty()){*/
            payment = paymentUtility.getPaymentRecord(orderId, amount, PaymentProvider.RAZORPAY);
            payment = paymentRepo.save(payment);
/*        }*/
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");
            paymentLinkRequest.put("accept_partial",false);
            Instant instant = Instant.now();
            long timeStampMillis = instant.toEpochMilli();
            paymentLinkRequest.put("expire_by", timeStampMillis);
            paymentLinkRequest.put("reference_id",orderId);
            paymentLinkRequest.put("description","Payment for order #" + orderId);

            paymentLinkRequest.put("customer", this.getCustomer(emailId, String.valueOf(Optional.of(payment).map(Payment::getId).orElse(null))));

            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);
            paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            return paymentLink.get("short_url").toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "something is wrong";
        }
    }

    public JSONObject getCustomer(String emailId, String paymentId){
        JSONObject customer = new JSONObject();
        customer.put("name","Prakash Parmar");
        customer.put("contact","8889977445");
        customer.put("email",emailId);
        customer.put("paymentId", paymentId);
        return customer;
    }
}
