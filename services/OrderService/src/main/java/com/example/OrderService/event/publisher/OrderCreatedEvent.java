package com.example.OrderService.event.publisher;



import com.example.OrderService.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEvent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(Order order) {
        System.out.println("🎯 ORDER SERVICE: Publishing OrderCreatedEvent - " + order.getOrderId());

        rabbitTemplate.convertAndSend(
                "order.exchange",
                "order.created",
                order
        );

        System.out.println("✅ ORDER SERVICE: OrderCreatedEvent sent successfully!");
    }
}