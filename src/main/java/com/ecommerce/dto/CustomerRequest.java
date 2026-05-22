package com.ecommerce.dto;

import jakarta.validation.constraints.*;

public class CustomerRequest {

    @NotBlank @Size(max = 50)
    private String name;

    @NotBlank @Email
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 200)
    private String address;

    @Size(max = 50)
    private String username;

    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
