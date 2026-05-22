package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.entity.*;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(productService.searchByName(search));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(productService.getAllBooks());
    }

    @GetMapping("/clothing")
    public ResponseEntity<List<Clothing>> getAllClothing() {
        return ResponseEntity.ok(productService.getAllClothing());
    }

    @GetMapping("/shoes")
    public ResponseEntity<List<Shoes>> getAllShoes() {
        return ResponseEntity.ok(productService.getAllShoes());
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createBook(request));
    }

    @PostMapping("/clothing")
    public ResponseEntity<Clothing> createClothing(@Valid @RequestBody ClothingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createClothing(request));
    }

    @PostMapping("/shoes")
    public ResponseEntity<Shoes> createShoes(@Valid @RequestBody ShoesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createShoes(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
