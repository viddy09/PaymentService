package com.example.paymentservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntiatePaymentDTO {
    private String cart_id;
    private Long contact;
    private int amount;
    private String email;
    private String order_id;
}
