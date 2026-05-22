package com.ecommerce.dto;

import jakarta.validation.constraints.*;

public class ClothingRequest {

    @NotBlank @Size(max = 50)
    private String name;

    @Min(0)
    private double price;

    @Size(max = 100)
    private String description;

    @NotBlank @Size(max = 50)
    private String color;

    @NotBlank @Size(max = 50)
    private String brand;

    @NotBlank @Size(max = 50)
    private String material;

    @NotBlank @Size(max = 20)
    private String gender;

    @NotBlank @Size(max = 10)
    private String size;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
}
