package com.example.paymentservice.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StripeEventDTO extends EventDTO{
    private String id;
    private String object;
    private String created;
    private CheckoutSessionData data;
    private boolean livemode;
    private int pending_webhooks;
    private RequestData request;
    private String type;

    // CheckoutSession Object
    @Getter
    @Setter
    public static class CheckoutSessionData {
        private CheckoutSessionObject object;
    }

    // Nested class representing the 'object' object within 'data'
    @Getter
    @Setter
    public static class CheckoutSessionObject {
        private String id;
        private String object;
        private Object afterExpiration;
        private Object allowPromotionCodes;
        private int amountSubtotal;
        private int amountTotal;
        private AutomaticTax automaticTax;
        private Object billingAddressCollection;
        private Object cancelUrl;
        private String clientReferenceId;
        private Object consent;
        private Object consentCollection;
        private long created;
        private String currency;
        private Object customer;
        private String customerCreation;
        private Object customerDetails;
        private Object customerEmail;
        private long expiresAt;
        private Object invoice;
        private InvoiceCreation invoiceCreation;
        private boolean livemode;
        private Object locale;
        private Map<String, Object> metadata;
        private String mode;
        private Object payment_intent;
        private Object payment_link;
        private String paymentMethodCollection;
        private Map<String, Object> paymentMethodOptions;
        private String[] paymentMethodTypes;
        private String paymentStatus;
        private PhoneNumberCollection phoneNumberCollection;
        private Object recoveredFrom;
        private Object setupIntent;
        private Object shippingAddressCollection;
        private Object shippingCost;
        private Object shippingDetails;
        private String[] shippingOptions;
        private String status;
        private Object submitType;
        private Object subscription;
        private String success_url;
        private TotalDetails total_details;
        private String url;
    }

    @Getter
    @Setter
    public static class AutomaticTax {
        private boolean enabled;
        private Object liability;
        private Object status;
    }

    @Getter
    @Setter
    public static class InvoiceCreation {
        private boolean enabled;
        private InvoiceData invoiceData;
    }

    @Getter
    @Setter
    public static class InvoiceData {
        private Object accountTaxIds;
        private Object customFields;
        private Object description;
        private Object footer;
        private Object issuer;
        private Map<String, Object> metadata;
        private Object renderingOptions;
    }

    @Getter
    @Setter
    public static class PhoneNumberCollection {
        private boolean enabled;
    }

    @Getter
    @Setter
    public static class TotalDetails {
        private int amountDiscount;
        private int amountShipping;
        private int amountTax;
    }

    @Getter
    @Setter
    public static class RequestData {
        private String id;
        private String idempotency_key;
    }
}
