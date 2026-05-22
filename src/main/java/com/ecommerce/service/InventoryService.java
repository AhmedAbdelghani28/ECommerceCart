package com.ecommerce.service;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.entity.Inventory;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product id: " + productId));
    }

    public Inventory createInventory(InventoryRequest req) {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + req.getProductId()));
        if (inventoryRepository.findByProductId(req.getProductId()).isPresent()) {
            throw new IllegalArgumentException("Inventory already exists for product id: " + req.getProductId());
        }
        return inventoryRepository.save(new Inventory(product, req.getTotalStock()));
    }

    public Inventory updateStock(Long productId, int newStock) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setTotalStock(newStock);
        return inventoryRepository.save(inventory);
    }
}
