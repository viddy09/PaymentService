package com.example.paymentservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RazorPayEventDTO extends EventDTO{
    private String account_id;
    private List<String> contains;
    private long created_at;
    private String entity;
    private String event;
    private Payload payload;

    @Getter
    @Setter
    public static class Payload {
        private Payment payment;
    }

    @Getter
    @Setter
    public static class Payment {
        private Entity entity;
    }

    @Getter
    @Setter
    public static class Entity {
        private AcquirerData acquirer_data;
        private int amount;
        private int amount_refunded;
        private int amount_transferred;
        private String bank;
        private boolean captured;
        private Card card;
        private String card_id;
        private String contact;
        private long created_at;
        private String currency;
        private String description;
        private String email;
        private String error_code;
        private String error_description;
        private String error_reason;
        private String error_source;
        private String error_step;
        private String fee;
        private String id;
        private boolean international;
        private String invoice_id;
        private String method;
        private List<String> notes;
        private String order_id;
        private String refund_status;
        private String status;
        private String tax;
        private String token_id;
        private String vpa;
        private String wallet;
    }

    @Getter
    @Setter
    public static class AcquirerData {
        private String auth_code;
        private String rrn;
    }

    @Getter
    @Setter
    public static class Card {
        private boolean emi;
        private String entity;
        private String id;
        private String iin;
        private boolean international;
        private String issuer;
        private String last4;
        private String name;
        private String network;
        private String sub_type;
        private String type;
    }
}
