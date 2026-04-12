package com.sweetflow.order_ms.dto;

import java.util.UUID;

public record OrderItemRequestDTO(
        UUID productId,
        Integer quantity
) {}