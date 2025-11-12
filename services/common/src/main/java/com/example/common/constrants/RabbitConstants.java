package com.example.common.constrants;


public class RabbitConstants {

    // Exchange
    public static final String ORDER_EXCHANGE = "order.exchange";

    // Queues
    public static final String ORDER_QUEUE = "order.queue";
    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String INVOICE_QUEUE = "invoice.queue";

    // Routing keys
    public static final String ORDER_KEY = "order.*";
    public static final String INVENTORY_KEY = "inventory.*";
    public static final String PAYMENT_KEY = "payment.*";
    public static final String ORDER_CREATED_KEY = "order.created";
    public static final String INVENTORY_RESERVED_KEY = "inventory.reserved";
    public static final String INVENTORY_REJECTED_KEY = "inventory.rejected";
    public static final String PAYMENT_REQUESTED_KEY = "payment.requested";
    public static final String PAYMENT_COMPLETED_KEY = "payment.completed";
    public static final String PAYMENT_FAILED_KEY = "payment.failed";
    public static final String INVOICE_CREATED_KEY = "invoice.created";

}