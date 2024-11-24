package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNom(String nom);
}
