package com.angMetal.payment.kafka;

import com.angMetal.payment.entity.TransactionElastic;
import com.angMetal.payment.entity.TransactionMySQL;
import com.angMetal.payment.service.ElasticsearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    private final ElasticsearchServiceImpl elasticsearchServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson ObjectMapper for parsing messages

    @Autowired
    public PaymentConsumer(ElasticsearchServiceImpl elasticsearchServiceImpl) {
        this.elasticsearchServiceImpl = elasticsearchServiceImpl;
    }

    /**
     * Kafka Listener method for processing payment-related messages.
     *
     * @param message the incoming message from the Kafka topic.
     */
    @KafkaListener(topics = "payment-events", groupId = "ms-payment-group")
    public void listenPaymentEvents(String message) {
        logger.info("Received payment event: {}", message);

        try {
            // Parse and process the message
            TransactionMySQL transactionMySQL = createTransactionFromMessage(message);

            // Save to MySQL and Elasticsearch
            elasticsearchServiceImpl.saveTransaction(transactionMySQL);

            // Log the transaction saved and indexed
            logger.info("Transaction saved and indexed: {}", transactionMySQL);

        } catch (Exception e) {
            logger.error("Error processing payment event: {}", message, e);
        }
    }

    /**
     * Parse the Kafka message and create a Transaction entity.
     *
     * @param message the raw message.
     * @return a populated Transaction entity for MySQL.
     */
    private TransactionMySQL createTransactionFromMessage(String message) {
        try {
            return objectMapper.readValue(message, TransactionMySQL.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse message into TransactionMySQL entity", e);
        }
    }
}
