package com.example.InventoryService.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import static com.example.common.constrants.RabbitConstants.*;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue inventoryOrderQueue() { return new Queue(INVENTORY_ORDER_QUEUE, false); }

    @Bean
    public Queue inventoryPaymentQueue() { return new Queue(INVENTORY_PAYMENT_QUEUE, false); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange(ORDER_EXCHANGE); }

    @Bean
    public Binding orderCreatedBinding(Queue inventoryOrderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(inventoryOrderQueue).to(exchange).with(ORDER_KEY);
    }

    @Bean
    public Binding paymentCreatedBinding(Queue inventoryPaymentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(inventoryPaymentQueue).to(exchange).with(PAYMENT_KEY);
    }


    // JSON converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate dùng nếu InventoryService muốn gửi message lại
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
