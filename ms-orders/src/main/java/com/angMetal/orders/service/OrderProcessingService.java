package com.angMetal.orders.service;

import com.angMetal.orders.entity.Payment;
import com.angMetal.orders.enums.StatusFacture;
import domain.Order;
import com.angMetal.orders.repositories.OrderRepository;
import domain.OrderStatus;
import domain.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<Long, Payment> kafkaTemplate;

    public OrderProcessingService(OrderRepository orderRepository, KafkaTemplate<Long, Payment> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Retrieves all orders from the database table.
     */
    public List<?> getAllOrders() {
        try {
            log.info("Fetching all orders from the database...");
            return orderRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving orders from the database", e);
            // Return an empty list or rethrow the exception based on your needs
            return List.of();
        }
    }


    private void processNewOrUpdatedOrder(Order order) {
        log.info("Processing new or updated order: {}", order);

        // Ensure the order is in a valid state to initiate payment
        if (order.getStatus() == OrderStatus.NEW) {
            // Create a Payment object for the order
            Payment payment = Payment.builder()
                    .orderId(order.getId())
                    .amount((double) (order.getPrice() * order.getProductCount())) // Calculate total amount
                    .status(StatusFacture.valueOf("INITIATED"))
                    .build();

            // Send the Payment object to the PAYMENTS Kafka topic
            kafkaTemplate.send(Topics.PAYMENTS, payment.getOrderId(), payment)
                    .addCallback(
                            success -> log.info("Payment message sent successfully for Order ID: {}", order.getId()),
                            failure -> log.error("Failed to send payment message for Order ID: {}", order.getId(), failure)
                    );
        } else {
            log.warn("Order with ID {} is not in a state to process payment. Current status: {}", order.getId(), order.getStatus());
        }
    }


}
