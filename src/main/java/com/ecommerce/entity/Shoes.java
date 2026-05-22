package com.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shoes")
@DiscriminatorValue("SHOES")
public class Shoes extends Product {

    private int size;

    @Column(length = 50)
    private String color;

    @Column(length = 50)
    private String material;

    @Column(length = 50)
    private String brand;

    @Column(name = "sport_type", length = 50)
    private String sportType;

    @Column(length = 20)
    private String gender;

    public Shoes() {}

    public Shoes(String name, double price, String description,
                 int size, String color, String material,
                 String brand, String sportType, String gender) {
        super(name, price, description);
        this.size = size;
        this.color = color;
        this.material = material;
        this.brand = brand;
        this.sportType = sportType;
        this.gender = gender;
    }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getSportType() { return sportType; }
    public void setSportType(String sportType) { this.sportType = sportType; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
