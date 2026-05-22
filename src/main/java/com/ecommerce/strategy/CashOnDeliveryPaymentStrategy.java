package com.ecommerce.strategy;

import org.springframework.stereotype.Component;

@Component
public class CashOnDeliveryPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentType() {
        return "CASH_ON_DELIVERY";
    }

    @Override
    public String resolveOrderStatus() {
        return "Completed";
    }
}
