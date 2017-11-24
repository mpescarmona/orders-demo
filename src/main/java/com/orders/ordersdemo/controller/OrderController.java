package com.orders.ordersdemo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.ordersdemo.domain.Message;
import com.orders.ordersdemo.domain.MessageOrder;
import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import com.orders.ordersdemo.exception.OrderNotFoundException;
import com.orders.ordersdemo.message.RabbitMqConfig;
import com.orders.ordersdemo.message.Sender;
import com.orders.ordersdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController()
@RequestMapping(value="/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Sender sender;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity < String > saveOrder(@RequestBody Order order) {

        if (isValid(order)) {
            Order existingOrder = orderRepository.findByOrderNumber(order.getOrderNumber());
            if (existingOrder != null) {
                if (order.getStatus() != null) {
                    existingOrder.setStatus(order.getStatus());
                }
                if (order.getSourceSystem() != null) {
                    existingOrder.setSourceSystem(order.getSourceSystem());
                }
                if (order.getVendor() != null) {
                    existingOrder.setVendor(order.getVendor());
                }
                existingOrder.setUpdatedTimestamp(new Date());

                sendMessage(RabbitMqConfig.ROUTING_KEY_ORDER_UPDATE, order.getOrderNumber());
                orderRepository.save(existingOrder);
            } else {
                order.setCreatedTimestamp(new Date());

                sendMessage(RabbitMqConfig.ROUTING_KEY_ORDER_CREATE, order.getOrderNumber());
                orderRepository.save(order);
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public Order show(@RequestParam(name = "orderNumber") String orderNumber) {

        Order foundOrder = orderRepository.findByOrderNumber(orderNumber);
        if (foundOrder != null) {
            return foundOrder;
        } else {
            throw new OrderNotFoundException(orderNumber);
        }
    }

    private void sendMessage(String routingKey, String orderNumber) {

        MessageOrder messageOrder = new MessageOrder(orderNumber, "http://localhost:8080/orders?orderNumber=" +
                orderNumber);
        Message message = new Message(UUID.randomUUID().toString(), routingKey, messageOrder);
        ObjectMapper jsonMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String jsonMessage;
        try {
            jsonMessage = jsonMapper.writeValueAsString(message);

            sender.send(RabbitMqConfig.EXCHANGE_NAME, routingKey, jsonMessage);
        } catch (JsonProcessingException e) {
            System.out.println("There were an error trying to generate a message with key [" + routingKey + "] for " +
                    "order [" + orderNumber + "]");
        }
    }

    private boolean isValid(Order order) {
        boolean result = true;
        if (order == null) {
            result = false;
        }
        if (result && order.getOrderNumber() == null) {
            result = false;
        }
        if (result && order.getStatus() == null) {
            result = false;
        }
        if (result && order.getSourceSystem() == null) {
            result = false;
        }
        if (result && !isValid(order.getVendor())) {
            result = false;
        }

        return result;
    }

    private boolean isValid(Vendor vendor) {
        boolean result = true;
        if (vendor == null) {
            result = false;
        }
        if (result && vendor.getName() == null) {
            result = false;
        }
        if (result && vendor.getNumber() == null) {
            result = false;
        }
        return result;
    }
}
