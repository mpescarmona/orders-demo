package com.orders.ordersdemo.domain;


public class Vendor {
    private String number;
    private String name;

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Vendor setNumber(String number) {
        this.number = number;
        return this;
    }

    public Vendor setName(String name) {
        this.name = name;
        return this;
    }
}

