package com.example.demo.event.publisher;


import static com.example.common.constrants.RabbitConstants.*;
import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishInventoryReservedEvent(OrderDTO orderDTO){
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                INVENTORY_RESERVED_KEY,
                orderDTO
        );
        System.out.println(" Gui sự kiện giữ tạm kho thành công");
    }

    public void publishInventoryFailedEvent(OrderDTO orderDTO){

        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                INVENTORY_REJECTED_KEY,
                orderDTO
        );
        System.out.println("Khong du ton kho, thay doi hoac dois");
    }
}
