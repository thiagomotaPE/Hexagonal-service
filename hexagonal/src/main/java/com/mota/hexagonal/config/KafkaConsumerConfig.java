package com.mota.hexagonal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mota.hexagonal.adapters.in.consumer.message.CustomerMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, CustomerMessage> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "mota");
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Deserializer customizado usando Jackson diretamente
        Deserializer<CustomerMessage> valueDeserializer = (topic, data) -> {
            if (data == null) return null;
            try {
                return objectMapper.readValue(data, CustomerMessage.class);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao deserializar CustomerMessage", e);
            }
        };

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CustomerMessage>
    kafkaListenerContainerFactory(ConsumerFactory<String, CustomerMessage> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CustomerMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}