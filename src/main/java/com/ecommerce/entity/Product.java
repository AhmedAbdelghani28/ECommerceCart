package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(length = 100)
    private String description;

    protected Product() {}

    protected Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name cannot be empty");
        if (name.length() > 50) throw new IllegalArgumentException("Product name cannot exceed 50 characters");
        this.name = name;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description cannot be empty");
        if (description.length() > 100) throw new IllegalArgumentException("Description cannot exceed 100 characters");
        this.description = description;
    }
}