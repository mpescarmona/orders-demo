package com.orders.ordersdemo.controller;

import com.orders.ordersdemo.domain.Order;
import com.orders.ordersdemo.domain.Vendor;
import com.orders.ordersdemo.exception.OrderNotFoundException;
import com.orders.ordersdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController()
@RequestMapping(value="/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

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

                orderRepository.save(existingOrder);
            } else {
                order.setCreatedTimestamp(new Date());

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
