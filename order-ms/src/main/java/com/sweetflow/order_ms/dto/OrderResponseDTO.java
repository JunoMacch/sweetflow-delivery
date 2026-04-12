package com.sweetflow.order_ms.dto;

import com.sweetflow.order_ms.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(UUID id,
                               Long orderNumber,
                               LocalDateTime dateTime,
                               OrderStatus status,
                               PaymentMethod paymentMethod,
                               DeliveryType deliveryType,
                               String coupon,
                               BigDecimal totalValue,
                               UUID clientId,
                               List<OrderItemResponseDTO> items) {

    public static OrderResponseDTO fromEntity(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getDateTime(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getDeliveryType(),
                order.getCoupon(),
                order.getTotalValue(),
                order.getClientId(),
                order.getItems().stream().map(OrderItemResponseDTO::fromEntity).toList()
        );
    }

}
