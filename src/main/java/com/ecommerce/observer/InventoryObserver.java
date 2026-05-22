package com.ecommerce.observer;

public interface InventoryObserver {
    void onLowStock(String productName, int availableQuantity);
}
