//package com.example.demo.event.listener;
//
//import com.example.common.constrants.RabbitConstants;
//import com.example.common.dto.orderdtos.OrderDTO;
//import com.example.common.dto.orderdtos.OrderItemDTO;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.messaging.handler.annotation.Header;
//
//@Component
//public class TestListener {
//    @RabbitListener(queues = RabbitConstants.INVENTORY_QUEUE)
//    public void handleOrderCreated(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
//
//        System.out.println("--- Bắt đầu xử lý sự kiện: " + routingKey + " ---");
//
//        System.out.println("🔔 INVENTORY SERVICE: Received OrderCreatedEvent with message: "+ orderDTO);
//        for(OrderItemDTO item : orderDTO.getOrderItemDTOs()) {
//            System.out.println(item);
//
//        }
//    }
//}
