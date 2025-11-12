package com.example.OrderService.event.publisher;


import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.example.common.constrants.RabbitConstants.*;

@Component
public class OrderPaymentEvent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishOrderCreated(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                PAYMENT_REQUESTED_KEY,
                orderDTO
        );
        System.out.println("✅ ORDER SERVICE: OrderCreatedEvent sent successfully!");
    }

}