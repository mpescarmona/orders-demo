package com.orders.ordersdemo.domain;


public class Vendor {
    private String number;
    private String name;

    public Vendor() {}

    public Vendor(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public Vendor setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Vendor setName(String name) {
        this.name = name;
        return this;
    }
}

