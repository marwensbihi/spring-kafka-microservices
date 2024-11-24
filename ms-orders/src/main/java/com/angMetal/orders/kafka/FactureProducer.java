package com.angMetal.orders.kafka;

import models.FactureEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactureProducer {

    private final KafkaTemplate<String, FactureEvent> kafkaTemplate;

    private static final String VENTE_TOPIC = "facture-vente-topic";
    private static final String ACHAT_TOPIC = "facture-achat-topic";

    /**
     * Send FactureEvent to Kafka topic based on the type of facture (VENTE or ACHAT).
     * @param factureEvent The facture event containing facture details.
     */
    public void sendFactureEvent(FactureEvent factureEvent) {
        String topic = getTopicForFactureType(factureEvent.getType());

        // Send FactureEvent to the appropriate Kafka topic
        kafkaTemplate.send(topic, factureEvent)
                .addCallback(
                        result -> System.out.println("Facture event sent successfully to topic: " + topic),
                        ex -> System.err.println("Error sending facture event to topic: " + topic)
                );
    }

    /**
     * Get the Kafka topic based on the facture type.
     * @param factureType The type of facture (VENTE or ACHAT).
     * @return The appropriate Kafka topic name.
     */
    private String getTopicForFactureType(String factureType) {
        if ("VENTE".equalsIgnoreCase(factureType)) {
            return VENTE_TOPIC;
        } else if ("ACHAT".equalsIgnoreCase(factureType)) {
            return ACHAT_TOPIC;
        } else {
            throw new IllegalArgumentException("Invalid facture type: " + factureType);
        }
    }
}
