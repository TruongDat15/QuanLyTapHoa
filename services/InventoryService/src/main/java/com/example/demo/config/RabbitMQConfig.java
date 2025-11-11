package com.example.demo.config;

import com.example.common.constrants.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public Queue inventoryQueue() { return new Queue(RabbitConstants.INVENTORY_QUEUE, false); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange(RabbitConstants.ORDER_EXCHANGE); }

    @Bean
    public Binding orderCreatedBinding(Queue inventoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(inventoryQueue).to(exchange).with(RabbitConstants.ORDER_KEY);
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
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(jsonMessageConverter());
//        return factory;
//    }
}
