package com.ecommerce.dto;

import jakarta.validation.constraints.*;

public class ShoesRequest {

    @NotBlank @Size(max = 50)
    private String name;

    @Min(0)
    private double price;

    @Size(max = 100)
    private String description;

    @Min(1)
    private int size;

    @NotBlank @Size(max = 50)
    private String color;

    @NotBlank @Size(max = 50)
    private String material;

    @NotBlank @Size(max = 50)
    private String brand;

    @NotBlank @Size(max = 50)
    private String sportType;

    @NotBlank @Size(max = 20)
    private String gender;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

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
