package com.orders.ordersdemo.domain;

public class MessageOrder {
    private String orderNumber;
    private String resource;

    public MessageOrder() {
    }

    public MessageOrder(String orderNumber, String resource) {
        this.orderNumber = orderNumber;
        this.resource = resource;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public MessageOrder setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public MessageOrder setResource(String resource) {
        this.resource = resource;
        return this;
    }
}
