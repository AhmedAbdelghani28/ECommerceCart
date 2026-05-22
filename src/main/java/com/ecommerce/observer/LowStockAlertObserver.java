package com.ecommerce.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LowStockAlertObserver implements InventoryObserver {

    private static final Logger log = LoggerFactory.getLogger(LowStockAlertObserver.class);

    @Override
    public void onLowStock(String productName, int availableQuantity) {
        log.warn("LOW STOCK ALERT: '{}' has only {} unit(s) remaining", productName, availableQuantity);
    }
}
