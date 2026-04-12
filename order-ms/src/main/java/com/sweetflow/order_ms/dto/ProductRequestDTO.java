package com.sweetflow.order_ms.dto;

import com.sweetflow.order_ms.entity.ProductCategory;

import java.math.BigDecimal;

public record ProductRequestDTO(String name,
                               String description,
                               BigDecimal price,
                               String weight,
                               ProductCategory category,
                               String imageUrl) {}
