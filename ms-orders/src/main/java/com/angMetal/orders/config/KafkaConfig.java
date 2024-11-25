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

    // KafkaTemplate for producing messages to Kafka topics
    @Bean
    public KafkaTemplate<String, FactureEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Configure the ProducerFactory for KafkaTemplate
    @Bean
    public ProducerFactory<String, FactureEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // Configuration properties for the Kafka producer (for KafkaTemplate)
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Kafka broker address
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Key serializer
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, FactureEventSerializer.class);  // Value serializer
        return configs;
    }
}
