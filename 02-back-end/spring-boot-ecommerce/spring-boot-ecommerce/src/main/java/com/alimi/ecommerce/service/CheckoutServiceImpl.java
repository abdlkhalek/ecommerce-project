package com.alimi.ecommerce.service;

import com.alimi.ecommerce.dao.CustomerRepository;
import com.alimi.ecommerce.dto.PaymentInfo;
import com.alimi.ecommerce.dto.Purchase;
import com.alimi.ecommerce.dto.PurchaseResponse;
import com.alimi.ecommerce.entity.Customer;
import com.alimi.ecommerce.entity.Order;
import com.alimi.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;

        // initialize stripe api with the secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

//        System.out.println("That is " + purchase.getOrder().getId());
        // retrieve the order info from dto
        Order order = purchase.getOrder();


        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with shipping and billing address
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();


        // check if the customer exists
        String theEmail = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if(customerFromDB != null) {
            // if not null means the customer exists
            customer = customerFromDB;
        }

        customer.add(order);

        // save to the database
        // error solved
        customerRepository.save(customer);


        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        // define a list of the payment method types
        List<String> paymentMethodTypes = new ArrayList<>();

        // add the types
        paymentMethodTypes.add("card");

        // create param map
        Map<String, Object> params = new HashMap<>();

        // add params
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "alimi purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        // create the payment intent with the client secret
        return PaymentIntent.create(params);
    }


    private String generateOrderTrackingNumber() {
        // generate a random UUID (Universally Unique Identifier)
        return UUID.randomUUID().toString();
    }
}
