package com.example.OrderService.event.handler;

import com.example.common.constrants.RabbitConstants;
import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class HandlerInventory {

    @RabbitListener(queues = RabbitConstants.INVENTORY_QUEUE)
    public void handleInventoryResponse(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){

        System.out.println("Nhân xự kiện"+ routingKey);
        if(RabbitConstants.INVENTORY_RESERVED_KEY.equals(routingKey)){
            System.out.println("Cap nhat don hang pending va goij thanh toasn");
        }else if(RabbitConstants.INVENTORY_REJECTED_KEY.equals(routingKey)){
            System.out.println("Không đủ tồn kho, vui lòng thử lại ");
        }
    }
}
