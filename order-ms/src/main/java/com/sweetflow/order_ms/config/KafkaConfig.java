package com.sweetflow.order_ms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetflow.order_ms.dto.OrderResponseDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@SuppressWarnings("deprecation")
public class KafkaConfig {

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("pedido-criado")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, OrderResponseDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Agora o IntelliJ não vai mais reclamar!
        JsonSerializer<OrderResponseDTO> jsonSerializer = new JsonSerializer<>(mapper);

        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, OrderResponseDTO> kafkaTemplate(ProducerFactory<String, OrderResponseDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
