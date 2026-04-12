package com.sweetflow.order_ms.dto;

import com.sweetflow.order_ms.entity.Order;
import com.sweetflow.order_ms.entity.OrderItem;
import com.sweetflow.order_ms.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponseDTO(UUID id,
                                   Integer quantity,
                                   BigDecimal unitPrice,
                                   BigDecimal subtotal,
                                   UUID productId,
                                   String productName) {

    public static OrderItemResponseDTO fromEntity(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getSubtotal(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName()
        );
    }

}
