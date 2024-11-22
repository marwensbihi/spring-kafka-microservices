package com.zatribune.spring.ecommerce.validator.listener;

import domain.Order;
import domain.Topics;
import com.zatribune.spring.ecommerce.validator.service.OrderValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderValidatorListener {

    private final OrderValidatorService orderValidatorService;
    private final KafkaTemplate<Long, Order> template;

    @Autowired
    public OrderValidatorListener(OrderValidatorService orderValidatorService, KafkaTemplate<Long, Order> template) {
        this.orderValidatorService = orderValidatorService;
        this.template = template;
    }

    @KafkaListener(id = "orderValidatorListener", topics = Topics.PAYMENTS, groupId = "validatorGroup")
    public void onEvent(Order order) {
        log.info("Received order for validation: {}", order);

        // Validate the order
        Order validatedOrder = orderValidatorService.validateAndReturnOrder(order);

        // Send validated/rejected order to the next Kafka topic
        template.send(Topics.VALIDATED, validatedOrder.getId(), validatedOrder);
        log.info("Sent validated order: {}", validatedOrder);
    }
}
