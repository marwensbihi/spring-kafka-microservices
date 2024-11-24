package com.zatribune.spring.ecommerce.stock.service;

public interface OrderService {

    void reserve(Order order);

    void confirm(Order order);
}
