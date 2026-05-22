package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clothing")
@DiscriminatorValue("CLOTHING")
public class Clothing extends Product {

    @Column(length = 10)
    private String size;

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String brand;

    @Column(length = 50)
    private String material;

    @Column(length = 20)
    private String gender;

    public Clothing() {}

    public Clothing(String name, double price, String description,
                    String color, String brand, String material,
                    String gender, String size) {
        super(name, price, description);
        this.color = color;
        this.brand = brand;
        this.material = material;
        this.gender = gender;
        this.size = size;
    }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
