package com.example.OrderService.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;


@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "order.exchange";

    // Queues
    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String INVOICE_QUEUE = "invoice.queue";

    // Routing keys
    public static final String ORDER_CREATED_KEY = "order.created";
    public static final String STOCK_RESERVED_KEY = "stock.reserved";
    public static final String STOCK_REJECTED_KEY = "stock.rejected";
    public static final String ORDER_PAYMENT_KEY = "order.payment.requested";
    public static final String PAYMENT_COMPLETED_KEY = "payment.completed";
    public static final String PAYMENT_FAILED_KEY = "payment.failed";
    public static final String INVOICE_CREATED_KEY = "invoice.created";

    @Bean
    public Queue inventoryQueue() { return new Queue(INVENTORY_QUEUE, false); }

    @Bean
    public Queue paymentQueue() { return new Queue(PAYMENT_QUEUE, false); }

    @Bean
    public Queue invoiceQueue() { return new Queue(INVOICE_QUEUE, false); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange(EXCHANGE); }

    // Bindings
    @Bean
    public Binding orderCreatedBinding(Queue inventoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(inventoryQueue).to(exchange).with(ORDER_CREATED_KEY);
    }

//    @Bean
//    public Binding orderPaymentBinding(Queue paymentQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(paymentQueue).to(exchange).with(ORDER_PAYMENT_KEY);
//    }

    @Bean
    public Binding invoiceBinding(Queue invoiceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(invoiceQueue).to(exchange).with(PAYMENT_COMPLETED_KEY);
    }

    // JSON converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
