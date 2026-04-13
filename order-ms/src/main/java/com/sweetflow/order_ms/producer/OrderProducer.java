package com.sweetflow.order_ms.producer;

import com.sweetflow.order_ms.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    public void sendOrderEvent(OrderResponseDTO order) {
        log.info("Enviando pedido #{} para o tópico Kafka...", order.orderNumber());

        kafkaTemplate.send("pedido-criado", order.id().toString(), order);
    }
}
