package com.example.OrderService.event.publisher;



import com.example.common.dto.orderdtos.OrderDTO;
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