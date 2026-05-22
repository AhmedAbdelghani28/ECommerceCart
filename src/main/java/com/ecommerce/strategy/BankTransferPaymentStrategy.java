package com.ecommerce.strategy;

import org.springframework.stereotype.Component;

@Component
public class BankTransferPaymentStrategy implements PaymentStrategy {

    @Override
    public String getPaymentType() {
        return "BANK_TRANSFER";
    }

    @Override
    public String resolveOrderStatus() {
        return "Pending";
    }
}
