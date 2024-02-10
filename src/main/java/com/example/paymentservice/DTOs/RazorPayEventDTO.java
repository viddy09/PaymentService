package com.example.paymentservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RazorPayEventDTO extends EventDTO{
    private String account_id;
    private List<String> contains;
    private long created_at;
    private String entity;
    private String event;
    private PayloadObject payload;

    @Getter
    @Setter
    public static class PayloadObject {
        private PaymentObject payment;
    }

    @Getter
    @Setter
    public static class PaymentObject {
        private EntityObject entity;
    }

    @Getter
    @Setter
    public static class EntityObject {
        private Object acquirer_data;
        private int amount;
        private int amount_refunded;
        private int amount_transferred;
        private Object bank;
        private boolean captured;
        private Object card;
        private String card_id;
        private String contact;
        private long created_at;
        private String currency;
        private Object description;
        private String email;
        private String entity;
        private String error_code;
        private String error_description;
        private Object error_reason;
        private Object error_source;
        private Object error_step;
        private Object fee;
        private String id;
        private boolean international;
        private Object invoice_id;
        private String method;
        private Map<String, Object> notes;
        private String order_id;
        private Object refund_status;
        private String status;
        private Object tax;
        private String token_id;
        private Object vpa;
        private Object wallet;
    }
}
