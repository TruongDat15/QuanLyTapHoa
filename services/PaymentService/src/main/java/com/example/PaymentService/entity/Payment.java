package com.example.PaymentService.entity;

import java.io.Serializable;

public class Payment implements Serializable {
    private String id;
    private String orderId;
    private double amount;
    private String status;

    // Constructors, Getters, Setters
    public Payment() {}

    public Payment(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = "PENDING";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Payment{id='" + id + "', orderId='" + orderId + "', amount=" + amount + ", status='" + status + "'}";
    }
}