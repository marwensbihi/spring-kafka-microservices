package com.angMetal.orders.kafka;

import models.FactureEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactureProducer {

    private final KafkaTemplate<String, FactureEvent> kafkaTemplate;

    private static final String FACTURE_EVENTS = "facture-events";
    private static final String STOCK_TOPIC = "stock-events";

    /**
     * Send FactureEvent to Kafka topic based on the type of facture (VENTE or ACHAT).
     * @param factureEvent The facture event containing facture details.
     */
    public void sendFactureEvent(FactureEvent factureEvent) {
        // Send FactureEvent to the facture-events Kafka topic
        kafkaTemplate.send(FACTURE_EVENTS, factureEvent)
                .addCallback(
                        result -> System.out.println("Facture event sent successfully to topic: " + FACTURE_EVENTS),
                        ex -> System.err.println("Error sending facture event to topic: " + FACTURE_EVENTS)
                );

        // Now, send the same FactureEvent to the stock-events Kafka topic for stock update
        sendStockEvent(factureEvent);
    }

    /**
     * Send the FactureEvent to the stock-events Kafka topic to update the stock.
     * @param factureEvent The facture event containing stock update information.
     */
    private void sendStockEvent(FactureEvent factureEvent) {
        // Send FactureEvent to the stock-events topic (no need for transformation)
        kafkaTemplate.send(STOCK_TOPIC, factureEvent)
                .addCallback(
                        result -> System.out.println("Stock event sent successfully to topic: " + STOCK_TOPIC),
                        ex -> System.err.println("Error sending stock event to topic: " + STOCK_TOPIC)
                );
    }
}
