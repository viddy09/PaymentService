package com.example.paymentservice.PaymentGateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Service
@Getter
@Setter
public class StripeGateway implements PaymentGateway{
    @Value(value = "${Stripe.apiKey}")
    private String stripeSecretKey;
    @Override
    public String generatePaymentlink(String orderId, Long phoneNumber, int amount, String emailId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        //Creating Product
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Gold Special");
        Product product = null;
        try {
            product = Product.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        //Creating Price
        Map<String, Object> params1 = new HashMap<>();
        params1.put("unit_amount", amount);
        params1.put("currency", "inr");
        params1.put("product", product.getId());
        Price price = null;
        try {
            price = Price.create(params1);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        Map<String, Object> params2 = getStringObjectMap(price);

        PaymentLink paymentLink = null;
        try {
            paymentLink =
                    PaymentLink.create(params2);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        return paymentLink.getUrl().toString();
    }

    private static Map<String, Object> getStringObjectMap(Price price) {
        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem1 = new HashMap<>();
        lineItem1.put(
                "price",
                price.getId()
        );
        lineItem1.put("quantity", 1);
        lineItems.add(lineItem1);
        Map<String, Object> params2 = new HashMap<>();
        params2.put("line_items", lineItems);

        Map<String, Object> redirect = new HashMap<>();
        redirect.put("url", "https://scaler.com?payment_id={CHECKOUT_SESSION_ID}");

        Map<String, Object> afterComp = new HashMap<>();
        afterComp.put("type", "redirect");
        afterComp.put("redirect", redirect);

        params2.put("after_completion", afterComp);
        return params2;
    }
}
