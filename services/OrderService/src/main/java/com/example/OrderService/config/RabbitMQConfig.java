package com.example.OrderService.config;



import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import static com.example.common.constrants.RabbitConstants.*;


@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() { return new TopicExchange(ORDER_EXCHANGE); }

    @Bean
    public Queue orderQueue() { return new Queue(ORDER_QUEUE, false); }
    @Bean
    public Binding inventoryResponseBinding(Queue inventoryQueue, TopicExchange exchange) {


        return BindingBuilder.bind(inventoryQueue)
                .to(exchange)
                .with(INVENTORY_KEY);
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
