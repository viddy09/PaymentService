package com.example.paymentservice.Services.PaymentGateways;

import com.example.paymentservice.Models.Payment;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripeGatewayFacade {

    //Creating Product
    public Product createProduct(){
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Gold Special");
        Product product = null;
        try {
            product = Product.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    //Creating Customer
    public Customer createCustomer(String orderId){
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("description", "Your Payment for OrderId"+orderId);
        /*RequestOptions options =
                RequestOptions.builder()
                        .setIdempotencyKey("KG5LxwFBepaKHyUD")
                        .build();*/
        Customer customer = null;
        try{
            customer = Customer.create(customerParams /*,options*/);
        } catch(StripeException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public Price createPrice(Product product, int amount){
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
        return price;
    }

    public Map<String, Object> getPaymentLinkParms(Price price, Payment payment) {
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

        //Adding PaymentId for future reference
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("paymentId", payment.getId());
        params2.put("metadata",metadata);

        return params2;
    }
}
