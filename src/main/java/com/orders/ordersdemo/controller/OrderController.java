package com.orders.ordersdemo.controller;

import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RestController()
@RequestMapping(value="/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order) {

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
            orderRepository.save(existingOrder);

            return existingOrder;

        } else {
            order.setCreatedTimestamp(new Date());
            orderRepository.save(order);

            return order;
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public Order show(@RequestParam(name = "orderNumber") String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}
