package com.angMetal.payment.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.angMetal.payment.service.OrderService;

@Slf4j
@Component
public class PaymentListener {


    private final OrderService orderService;

    @Autowired
    public PaymentListener(OrderService orderService) {
        this.orderService = orderService;
    }

/*
    @KafkaListener(id = KafkaIds.ORDERS, topics = Topics.ORDERS, groupId = KafkaGroupIds.PAYMENTS)
    public void onEvent(Order o) {
        log.info("Received: {}" , o);
        if (o.getStatus().equals(OrderStatus.NEW))
            orderService.reserve(o);
        else
            orderService.confirm(o);
    }*/





}
