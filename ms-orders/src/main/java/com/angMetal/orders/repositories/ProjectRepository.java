package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Projet,Long> {
}
