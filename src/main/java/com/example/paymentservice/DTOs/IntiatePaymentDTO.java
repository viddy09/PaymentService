package com.example.paymentservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntiatePaymentDTO {
    private String OrderId;
    private Long contact;
    private int amount;
    private String email;
}
