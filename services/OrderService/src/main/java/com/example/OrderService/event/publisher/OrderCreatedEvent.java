package com.example.OrderService.event.publisher;



import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.example.common.constrants.RabbitConstants.*;

@Component
public class OrderCreatedEvent {

    private final RabbitTemplate rabbitTemplate;

    public OrderCreatedEvent(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                ORDER_CREATED_KEY,
                orderDTO
        );
        System.out.println("✅ 1.0 ORDER SERVICE: OrderCreatedEvent sent successfully!");
    }

    public void publishOrderAndPaymentStatus(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                ORDER_PENDING_KEY,
                orderDTO
        );
        System.out.println("✅ 1.4 SU kien OrderAndPaymentStatus sent successfully!");
    }

    public void publishOrderCompleted(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                ORDER_COMPLETED_KEY,
                orderDTO
        );
        System.out.println("✅ 1.6 SU kien OrderCompleted sent successfully!" + orderDTO);
    }
}