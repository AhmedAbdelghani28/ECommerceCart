package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @Column(name = "total_stock", nullable = false)
    private int totalStock;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity = 0;

    public Inventory() {}

    public Inventory(Product product, int totalStock) {
        if (totalStock < 0) throw new IllegalArgumentException("Total stock cannot be negative");
        this.product = product;
        this.totalStock = totalStock;
    }

    public boolean canReserve(int amount) {
        return (totalStock - reservedQuantity) >= amount;
    }

    public void reserveQuantity(int amount) {
        if (!canReserve(amount)) throw new RuntimeException("Not enough stock");
        reservedQuantity += amount;
    }

    public void releaseReservation(int amount) {
        if (amount > reservedQuantity) throw new IllegalArgumentException("Cannot release more than reserved");
        reservedQuantity -= amount;
    }

    public void confirmPurchase(int amount) {
        if (amount > reservedQuantity) throw new IllegalArgumentException("Cannot confirm more than reserved");
        totalStock -= amount;
        reservedQuantity -= amount;
    }

    public int getAvailableStock() {
        return totalStock - reservedQuantity;
    }

    public Long getId() { return id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) {
        if (totalStock < 0) throw new IllegalArgumentException("Total stock cannot be negative");
        this.totalStock = totalStock;
    }

    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}
