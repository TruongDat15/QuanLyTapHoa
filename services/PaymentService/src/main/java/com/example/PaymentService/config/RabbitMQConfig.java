package com.example.PaymentService.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.common.constrants.RabbitConstants.*;


@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() { return new TopicExchange(ORDER_EXCHANGE); }

    @Bean
    public Queue paymentQueue() { return new Queue(PAYMENT_QUEUE, false); }
    @Bean
    public Binding inventoryResponseBinding(Queue paymentQueue, TopicExchange exchange) {


        return BindingBuilder.bind(paymentQueue)
                .to(exchange)
                .with(PAYMENT_REQUESTED_KEY);
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
