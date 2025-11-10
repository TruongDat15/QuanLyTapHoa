package com.example.demo.event.listener;

import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {

    @RabbitListener(queues = "inventory.queue")
    public void handleOrderCreated(OrderDTO orderDTO) {
        System.out.println("🔔 INVENTORY SERVICE: Received OrderCreatedEvent with message: "+ orderDTO);

    }
}
