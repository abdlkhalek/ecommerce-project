package com.alimi.ecommerce.service;

import com.alimi.ecommerce.dto.PaymentInfo;
import com.alimi.ecommerce.dto.Purchase;
import com.alimi.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
