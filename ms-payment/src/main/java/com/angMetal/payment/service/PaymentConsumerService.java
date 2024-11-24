package com.angMetal.payments.service;

import com.angMetal.payments.Repository.TransactionRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import models.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;




@Slf4j
@Service
public class PaymentConsumerService {


    private final TransactionRepository transactionRepository;
    private final PaymentRepository transactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.payment-ack}")
    private String paymentAckTopic;

    public PaymentConsumerService(TransactionRepository transactionRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payment", groupId = "payment-group")
    public void consumePaymentOrder(Order order) {
        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setOrderId(order.getId());
        transaction.setCustomerId(order.getCustomerId());
        transaction.setAmount(order.getPrice());
        transaction.setPaymentStatus("SUCCESS");
        transactionRepository.save(transaction);

        // Send acknowledgment to the order service
        kafkaTemplate.send(paymentAckTopic, transaction);
    }
}