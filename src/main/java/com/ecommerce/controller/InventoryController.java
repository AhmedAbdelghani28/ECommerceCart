package com.ecommerce.controller;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.entity.Inventory;
import com.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
    }

    @PutMapping("/product/{productId}/stock")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long productId,
                                                  @RequestParam int stock) {
        return ResponseEntity.ok(inventoryService.updateStock(productId, stock));
    }
}
