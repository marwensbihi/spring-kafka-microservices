package com.zatribune.spring.ecommerce.validator.service;

import org.springframework.stereotype.Service;

@Service
public class OrderValidatorService {

    public boolean validate(Order order) {
        // Example validation logic:
        // 1. Check if order has valid price (greater than 0)
        if (order.getPrice() <= 0) {
            return false;
        }
        // 2. Additional checks can go here (e.g., product availability, customer validation)
        // For now, we are assuming a simple validation.
        return true; // Order is valid
    }

    public Order validateAndReturnOrder(Order order) {
        if (validate(order)) {
            order.setStatus(OrderStatus.NEW); // Mark as new or validated
        } else {
            order.setStatus(OrderStatus.REJECTED); // Reject if validation fails
        }
        return order;
    }
}
