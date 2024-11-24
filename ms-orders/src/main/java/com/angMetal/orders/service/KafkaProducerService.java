package com.angMetal.orders.kafka;

import models.FactureEvent;
import com.angMetal.orders.config.KafkaProducerConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, FactureEvent> kafkaTemplate;
    private final KafkaProducerConfig kafkaProducerConfig;

    private KafkaProducer<String, FactureEvent> producer;

    private static final String FACTURE_TOPIC = "facture-topic";

    /**
     * Initializes the KafkaProducer instance after the application context is loaded.
     */
    @PostConstruct
    public void init() {
        this.producer = kafkaProducerConfig.kafkaProducer();
    }

    /**
     * Send a FactureEvent to Kafka.
     * @param factureEvent The facture event to be sent.
     */
    public void sendFactureEvent(FactureEvent factureEvent) {
        try {
            // Create a producer record
            ProducerRecord<String, FactureEvent> record = new ProducerRecord<>(FACTURE_TOPIC, factureEvent.getFactureId().toString(), factureEvent);

            // Send the record asynchronously
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    // Handle failure
                    exception.printStackTrace();
                } else {
                    // Handle success
                    System.out.println("Message sent successfully to Kafka topic: " + FACTURE_TOPIC);
                }
            });

        } catch (Exception e) {
            // Handle any exceptions during the Kafka send operation
            e.printStackTrace();
        }
    }

    /**
     * Alternative method using KafkaTemplate for easier sending.
     * @param factureEvent The facture event to be sent.
     */
    public void sendFactureEventWithTemplate(FactureEvent factureEvent) {
        kafkaTemplate.send(FACTURE_TOPIC, factureEvent.getFactureId().toString(), factureEvent)
                .addCallback(result -> {
                    System.out.println("Message sent successfully to Kafka topic: " + FACTURE_TOPIC);
                }, ex -> {
                    ex.printStackTrace();
                });
    }
}
