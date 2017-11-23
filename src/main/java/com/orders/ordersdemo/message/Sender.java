package com.orders.ordersdemo.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 1000L)
    public void send(String exchange, String routingKey, Object message) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}