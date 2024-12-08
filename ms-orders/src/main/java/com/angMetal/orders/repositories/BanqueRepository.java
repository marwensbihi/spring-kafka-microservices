package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Banque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepository extends JpaRepository<Banque, Long> {
}
