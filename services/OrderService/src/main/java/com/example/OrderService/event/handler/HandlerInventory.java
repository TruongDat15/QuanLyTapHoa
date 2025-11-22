package com.example.OrderService.event.handler;

import static com.example.common.constrants.RabbitConstants.*;

import com.example.OrderService.event.publisher.OrderCreatedEvent;
import com.example.OrderService.service.OrderService;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.enums.OrderStatus;
import com.example.common.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandlerInventory {

    private final OrderService orderService;
    private final OrderCreatedEvent orderCreatedEvent;

    @RabbitListener(queues = ORDER_QUEUE)
    public void handleInventoryResponse(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey){

        System.out.println("Nhân xự kiện "+ routingKey);

        if(INVENTORY_RESERVED_KEY.equals(routingKey)){

            System.out.println("✅ 1.4 Tồn kho đã được giữ, cập nhật PaymentStatus thành PENDING cho đơn hàng: " + orderDTO.getOrderId());
            orderService.updateOrder(orderDTO.getOrderId(), PaymentStatus.PENDING);

        }else if(INVENTORY_REJECTED_KEY.equals(routingKey)){

            // đơn hàng vẫn nháp thanh toán
            System.out.println("Không đủ tồn kho, vui lòng thử lại ");
        }

        // gui su kien thanh toan cho notification service
        orderCreatedEvent.publishOrderAndPaymentStatus(orderDTO);

    }
}
