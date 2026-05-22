package com.ecommerce.observer;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class InventoryEventPublisher {

    static final int LOW_STOCK_THRESHOLD = 5;

    private final List<InventoryObserver> observers;

    public InventoryEventPublisher(List<InventoryObserver> observers) {
        this.observers = observers;
    }

    public void checkAndNotify(String productName, int availableQuantity) {
        if (availableQuantity <= LOW_STOCK_THRESHOLD) {
            observers.forEach(o -> o.onLowStock(productName, availableQuantity));
        }
    }
}
