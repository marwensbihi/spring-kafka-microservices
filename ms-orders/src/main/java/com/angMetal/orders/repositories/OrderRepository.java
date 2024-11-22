package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
