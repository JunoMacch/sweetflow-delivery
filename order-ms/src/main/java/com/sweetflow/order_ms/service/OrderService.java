package com.sweetflow.order_ms.service;

import com.sweetflow.order_ms.dto.OrderRequestDTO;
import com.sweetflow.order_ms.dto.OrderResponseDTO;
import com.sweetflow.order_ms.entity.Order;
import com.sweetflow.order_ms.entity.OrderItem;
import com.sweetflow.order_ms.entity.OrderStatus;
import com.sweetflow.order_ms.entity.Product;
import com.sweetflow.order_ms.exception.OrderNotFoundException;
import com.sweetflow.order_ms.exception.ProductNotFoundException;
import com.sweetflow.order_ms.repository.OrderRepository;
import com.sweetflow.order_ms.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDTO createOrder (OrderRequestDTO orderRequestDTO) {

        log.info("Iniciando a criação de um novo pedido para o cliente: {}", orderRequestDTO.clientId());

        Order order = new Order();
        order.setClientId(orderRequestDTO.clientId());
        order.setOrderNumber(System.currentTimeMillis() % 10000);
        order.setDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentMethod(orderRequestDTO.paymentMethod());
        order.setDeliveryType(orderRequestDTO.deliveryType());
        order.setCoupon(orderRequestDTO.coupon());

        BigDecimal totalValue = BigDecimal.ZERO;

        List<OrderItem> items = new ArrayList<>();
        for (var itemDto : orderRequestDTO.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Nenhum doce encontrado com o ID: " + itemDto.productId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setUnitPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(itemDto.quantity()));
            orderItem.setSubtotal(subtotal);

            totalValue = totalValue.add(subtotal);
            items.add(orderItem);
        }

        order.setItems(items);
        order.setTotalValue(totalValue);

        Order savedOrder = orderRepository.save(order);

        log.info("Pedido criado com sucesso! Nº do Pedido: {} | Total: R$ {}", savedOrder.getOrderNumber(), savedOrder.getTotalValue());

        return OrderResponseDTO.fromEntity(savedOrder);
    }

    public OrderResponseDTO getOrderById(UUID id) {
        log.info("Consultando status do pedido ID: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado com o ID: + id"));

        return OrderResponseDTO.fromEntity(order);
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID id, OrderStatus newStatus) {
        log.info("Atualizando status do pedido {} para {}", id, newStatus);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado com o ID: + id"));

        order.setStatus(newStatus);

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDTO.fromEntity(savedOrder);
    }

    public List<OrderResponseDTO> getOrdersByClientId(UUID clientId) {
        log.info("Buscando histórico de pedidos do cliente: {}", clientId);

        return orderRepository.findByClientIdOrderByDateTimeDesc(clientId)
                .stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();
    }
}
