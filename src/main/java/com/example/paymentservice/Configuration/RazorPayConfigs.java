package com.example.paymentservice.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfigs {
    @Value("${razorpay.key.id}")
    private String razorPayClientKey;

    @Value("${razorpay.key.secret}")
    private String razorPayClientSecret;
    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razorPayClientKey, razorPayClientSecret);
    }
}
