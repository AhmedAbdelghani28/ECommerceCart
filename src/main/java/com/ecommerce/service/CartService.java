package com.ecommerce.service;

import com.ecommerce.entity.*;
import com.ecommerce.exception.InsufficientStockException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository, ProductRepository productRepository,
                       InventoryRepository inventoryRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public Cart getOrCreateCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId).orElseGet(() -> {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
            return cartRepository.save(new Cart(customer));
        });
    }

    public Cart addItem(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than 0");

        Cart cart = getOrCreateCart(customerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productId));
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));

        CartItem existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null);

        if (existing != null) {
            if (!inventory.canReserve(quantity)) {
                throw new InsufficientStockException("Not enough stock for: " + product.getName());
            }
            inventory.reserveQuantity(quantity);
            inventoryRepository.save(inventory);
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
        } else {
            if (!inventory.canReserve(quantity)) {
                throw new InsufficientStockException("Not enough stock for: " + product.getName());
            }
            inventory.reserveQuantity(quantity);
            inventoryRepository.save(inventory);
            cartItemRepository.save(new CartItem(cart, product, quantity));
        }

        return cartRepository.findByCustomerId(customerId).orElse(cart);
    }

    public Cart updateItemQuantity(Long customerId, Long productId, int newQuantity) {
        Cart cart = getOrCreateCart(customerId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (newQuantity <= 0) {
            inventory.releaseReservation(item.getQuantity());
            inventoryRepository.save(inventory);
            cartItemRepository.delete(item);
            return cartRepository.findById(cart.getId()).orElse(cart);
        }

        int diff = newQuantity - item.getQuantity();
        if (diff > 0) {
            if (!inventory.canReserve(diff)) throw new InsufficientStockException("Not enough stock");
            inventory.reserveQuantity(diff);
        } else if (diff < 0) {
            inventory.releaseReservation(-diff);
        }
        inventoryRepository.save(inventory);
        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    public Cart removeItem(Long customerId, Long productId) {
        Cart cart = getOrCreateCart(customerId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventory.releaseReservation(item.getQuantity());
        inventoryRepository.save(inventory);
        cartItemRepository.delete(item);
        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    public void clearCart(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        for (CartItem item : cart.getItems()) {
            inventoryRepository.findByProductId(item.getProduct().getId()).ifPresent(inv -> {
                inv.releaseReservation(item.getQuantity());
                inventoryRepository.save(inv);
            });
        }
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
