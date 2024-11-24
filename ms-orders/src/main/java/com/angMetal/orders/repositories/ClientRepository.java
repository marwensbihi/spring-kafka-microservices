package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
