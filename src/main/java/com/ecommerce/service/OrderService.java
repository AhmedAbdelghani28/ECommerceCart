package com.ecommerce.service;

import com.ecommerce.dto.CheckoutRequest;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.observer.InventoryEventPublisher;
import com.ecommerce.repository.*;
import com.ecommerce.strategy.PaymentStrategy;
import com.ecommerce.strategy.PaymentStrategyRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;
    private final PaymentStrategyRegistry paymentStrategyRegistry;
    private final InventoryEventPublisher inventoryEventPublisher;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
                        CustomerRepository customerRepository, InventoryRepository inventoryRepository,
                        PaymentStrategyRegistry paymentStrategyRegistry,
                        InventoryEventPublisher inventoryEventPublisher) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.inventoryRepository = inventoryRepository;
        this.paymentStrategyRegistry = paymentStrategyRegistry;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    public Order checkout(Long customerId, CheckoutRequest req) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty or does not exist"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        PaymentStrategy paymentStrategy = paymentStrategyRegistry.get(req.getPaymentType());

        Customer customer = cart.getCustomer();
        Order order = new Order(customer);
        order.setPaymentType(paymentStrategy.getPaymentType());

        for (CartItem cartItem : cart.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(cartItem.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
            inventory.confirmPurchase(cartItem.getQuantity());
            inventoryRepository.save(inventory);

            inventoryEventPublisher.checkAndNotify(
                    cartItem.getProduct().getName(), inventory.getAvailableStock());

            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(),
                    cartItem.getQuantity(), cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
        }

        order.calculateTotal();
        order.setStatus(paymentStrategy.resolveOrderStatus());

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
