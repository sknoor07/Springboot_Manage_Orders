package com.rbalazs.stock.repository;

import com.rbalazs.stock.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
