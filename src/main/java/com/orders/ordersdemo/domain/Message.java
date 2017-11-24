package com.orders.ordersdemo.domain;

public class Message {
    private String eventId;
    private String eventType;
    private MessageOrder order;

    public Message() {
    }

    public Message(String eventId, String eventType, MessageOrder order) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.order = order;
    }

    public String getEventId() {
        return eventId;
    }

    public Message setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public Message setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public MessageOrder getOrder() {
        return order;
    }

    public Message setOrder(MessageOrder order) {
        this.order = order;
        return this;
    }
}
