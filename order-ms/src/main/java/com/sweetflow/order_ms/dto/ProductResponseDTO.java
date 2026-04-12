package com.sweetflow.order_ms.dto;

import com.sweetflow.order_ms.entity.Product;
import com.sweetflow.order_ms.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(UUID id,
                                 String name,
                                 String description,
                                 BigDecimal price,
                                 String weight,
                                 ProductCategory category,
                                 String imageUrl,
                                 Boolean active) {

    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getWeight(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive()
        );
    }
}
