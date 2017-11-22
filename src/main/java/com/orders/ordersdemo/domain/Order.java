package com.orders.ordersdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

public class Order {
    @Id
    private String id;
    @Indexed(unique = true)
    private String orderNumber;
    private String status;
    private String sourceSystem;
    private Vendor vendor;
    private Date createdTimestamp;
    private Date updatedTimestamp;


    public Order() {}

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public Order setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
        return this;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Order setVendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Order setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public Order setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
        return this;
    }
}
