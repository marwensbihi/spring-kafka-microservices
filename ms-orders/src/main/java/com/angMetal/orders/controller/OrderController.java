package com.angMetal.orders.controller;

import com.angMetal.orders.service.OrderProcessingService;
import domain.Order;
import domain.OrderStatus;
import domain.Topics;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final AtomicLong id = new AtomicLong();
    private final KafkaTemplate<Long, Order> kafkaTemplate;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;

    private final OrderProcessingService orderService;


    @Autowired
    public OrderController(KafkaTemplate<Long, Order> kafkaTemplate, StreamsBuilderFactoryBean kafkaStreamsFactory, OrderProcessingService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
        this.orderService = orderService;
    }



    // Asynchronous method to send order and return the order created
    @PostMapping
    public CompletableFuture<Order> create(@RequestBody Order order) {
        order.setId(id.incrementAndGet());
        order.setStatus(OrderStatus.NEW);
       log.info("Sending order: {}", order);

        // Return a CompletableFuture instead of blocking the thread
        return kafkaTemplate.send(Topics.ORDERS, order.getId(), order)
                .completable()
                .thenApply(SendResult::getProducerRecord)
                .thenApply(producerRecord -> producerRecord.value())
                .exceptionally(ex -> {
                   log.error("Failed to send order to Kafka: {}", order, ex);
                    throw new RuntimeException("Failed to send order", ex);
                });
    }


    // Retrieve all orders from the Kafka Streams store
    @GetMapping
    public List<?> getAllOrders() {
        try {
            log.info("Fetching all orders from the service...");
            return orderService.getAllOrders();
        } catch (Exception e) {
            log.error("Error while fetching orders from the service", e);
            // Handle the error appropriately, e.g., return an empty list or a meaningful error response
            return List.of();
        }
    }
}
