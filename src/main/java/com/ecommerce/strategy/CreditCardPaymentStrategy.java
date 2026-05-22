package com.ecommerce.strategy;

import org.springframework.stereotype.Component;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }

    @Override
    public String resolveOrderStatus() {
        return "Completed";
    }
}
