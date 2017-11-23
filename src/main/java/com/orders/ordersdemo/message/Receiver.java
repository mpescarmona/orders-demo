package com.orders.ordersdemo.message;

public class Receiver {

    public static final String RECEIVE_METHOD_NAME = "receiveMessage";

    public void receiveMessage(String message) {
        System.out.println("[Receiver] has been received the message \"" + message + '"');
    }
}