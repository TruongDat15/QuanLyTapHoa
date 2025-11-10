package com.example.OrderService.event.publisher;



import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.event.OrderEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEvent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                "order.exchange",
                "order.created",
                orderDTO
        );
        System.out.println("✅ ORDER SERVICE: OrderCreatedEvent sent successfully!");
    }

}