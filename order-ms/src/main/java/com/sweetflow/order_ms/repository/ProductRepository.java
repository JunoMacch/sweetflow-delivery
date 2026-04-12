package com.sweetflow.order_ms.repository;

import com.sweetflow.order_ms.entity.Product;
import com.sweetflow.order_ms.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory (ProductCategory category);
}

