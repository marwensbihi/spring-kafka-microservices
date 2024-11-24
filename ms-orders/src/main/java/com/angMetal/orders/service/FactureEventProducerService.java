package com.angMetal.orders.service;

import models.FactureEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FactureEventProducerService {

    private static final String FACTURE_TOPIC = "facture-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Method to send a FactureEvent message to Kafka
    public void sendFactureEvent(FactureEvent factureEvent) {
        // Convert FactureEvent to JSON or String format if necessary
        String message = factureEvent.toString(); // Or use a JSON library like Jackson

        // Send message to Kafka
        kafkaTemplate.send(FACTURE_TOPIC, message);
    }
}
