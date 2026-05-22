package com.ecommerce.controller;

import com.ecommerce.dto.CartItemRequest;
import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(customerId));
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<Cart> addItem(@PathVariable Long customerId,
                                        @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(customerId, request.getProductId(), request.getQuantity()));
    }

    @PutMapping("/{customerId}/items/{productId}")
    public ResponseEntity<Cart> updateItem(@PathVariable Long customerId,
                                           @PathVariable Long productId,
                                           @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(customerId, productId, quantity));
    }

    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<Cart> removeItem(@PathVariable Long customerId,
                                           @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItem(customerId, productId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
