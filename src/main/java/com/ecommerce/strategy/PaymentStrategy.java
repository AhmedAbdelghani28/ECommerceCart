package com.ecommerce.strategy;

public interface PaymentStrategy {
    String getPaymentType();
    String resolveOrderStatus();
}
