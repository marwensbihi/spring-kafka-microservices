package com.angMetal.payment.service;

import com.angMetal.payment.Repository.TransactionRepository;
import com.angMetal.payment.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import models.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class PaymentConsumerService {

    private final TransactionRepository transactionRepository;

    public PaymentConsumerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "payment", groupId = "payment-group")
    public void consumePayment(Order order) {
        // Save transaction
        Transaction transaction = new Transaction();
        transaction.setMontant((double) order.getPrice());
        transaction.setDateTransaction(new Date());
        transaction.setTypeDeTransaction(order.getType());
        transaction.setFactureVenteId(order.getFactureVenteId());
        transaction.setFactureAchatId(order.getFactureAchatId());
        transactionRepository.save(transaction);

        log.info("Transaction saved successfully for Order ID: {}", order.getId());
    }
}
