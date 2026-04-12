package com.sweetflow.order_ms.dto;

import com.sweetflow.order_ms.entity.DeliveryType;
import com.sweetflow.order_ms.entity.PaymentMethod;

import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(
        UUID clientId,
        PaymentMethod paymentMethod,
        DeliveryType deliveryType,
        String coupon,
        List<OrderItemRequestDTO> items
) {}
