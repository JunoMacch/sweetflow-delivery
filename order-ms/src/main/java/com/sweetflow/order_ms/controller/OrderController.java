package com.sweetflow.order_ms.controller;

import com.sweetflow.order_ms.dto.OrderRequestDTO;
import com.sweetflow.order_ms.dto.OrderResponseDTO;
import com.sweetflow.order_ms.dto.UpdateOrderStatusDTO;
import com.sweetflow.order_ms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusDTO dto) {
        OrderResponseDTO responseDTO = orderService.updateOrderStatus(id, dto.status());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderResponseDTO>> getClientOrders(@PathVariable UUID clientId) {
        List<OrderResponseDTO> responses = orderService.getOrdersByClientId(clientId);
        return ResponseEntity.ok(responses);
    }
}
