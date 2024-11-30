package com.angMetal.orders.config;

import com.angMetal.orders.kafka.FactureEventSerializer;
import models.FactureEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    // Configure Producer Factory for KafkaTemplate
    @Bean
    public ProducerFactory<String, FactureEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // KafkaTemplate for producing messages to Kafka topics
    @Bean
    public KafkaTemplate<String, FactureEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Configuration properties for the Kafka producer (for KafkaTemplate)
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, FactureEventSerializer.class);
        return configs;
    }
}

