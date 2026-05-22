package com.ecommerce.strategy;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyRegistry {

    private final Map<String, PaymentStrategy> strategies;

    public PaymentStrategyRegistry(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(PaymentStrategy::getPaymentType, Function.identity()));
    }

    public PaymentStrategy get(String paymentType) {
        PaymentStrategy strategy = strategies.get(paymentType.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType +
                    ". Supported: " + strategies.keySet());
        }
        return strategy;
    }
}
