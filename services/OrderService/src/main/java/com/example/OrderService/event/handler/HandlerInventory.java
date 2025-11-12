package com.example.OrderService.event.handler;

import static com.example.common.constrants.RabbitConstants.*;

import com.example.OrderService.entity.Order;
import com.example.OrderService.service.OrderService;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandlerInventory {

    private final OrderService orderService;

    @RabbitListener(queues = ORDER_QUEUE)
    public void handleInventoryResponse(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){

        System.out.println("Nhân xự kiện "+ routingKey);

        if(INVENTORY_RESERVED_KEY.equals(routingKey)){

            System.out.println("Cap nhat don hang pending va goij thanh toasn");
            orderService.updateStatus(orderDTO.getOrderId(), OrderStatus.PENDING);

        }else if(INVENTORY_REJECTED_KEY.equals(routingKey)){
            System.out.println("Không đủ tồn kho, vui lòng thử lại ");
        }
    }
}
