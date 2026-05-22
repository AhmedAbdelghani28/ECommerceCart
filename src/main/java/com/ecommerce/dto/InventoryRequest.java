package com.ecommerce.dto;

import jakarta.validation.constraints.*;

public class InventoryRequest {

    @NotNull
    private Long productId;

    @Min(0)
    private int totalStock;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getTotalStock() { return totalStock; }
    public void setTotalStock(int totalStock) { this.totalStock = totalStock; }
}
