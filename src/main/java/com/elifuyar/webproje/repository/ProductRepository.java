package com.elifuyar.webproje.repository;

import com.elifuyar.webproje.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}