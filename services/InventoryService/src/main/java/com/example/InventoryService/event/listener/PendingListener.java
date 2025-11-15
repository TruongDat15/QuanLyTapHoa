package com.example.InventoryService.event.listener;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.InventoryService.event.publisher.InventoryPublisher;
import com.example.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Header;
import static com.example.common.constrants.RabbitConstants.*;

@Component
@RequiredArgsConstructor
public class PendingListener {

    private final ProductService productService;
    private final InventoryPublisher publisher;

    @RabbitListener(queues = INVENTORY_ORDER_QUEUE)
    public void handleOrderCreated(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        System.out.println("🔔 INVENTORY SERVICE: Received OrderCreatedEvent with message: "+ orderDTO);

        if(ORDER_CREATED_KEY.equals(routingKey)){
            try{
               // kiểm tra và giữ tồn kho
                productService.reserveStock(orderDTO);
                // gửi tin tồn kho khả dụng , cập nhật đơn hàng pending
                publisher.publishInventoryReservedEvent(orderDTO);

                // gui sư kien thanh toan
                publisher.publishPaymentEvent(orderDTO);
                System.out.println("Gui sư kiên thanh toan len Mq");
            } catch (Exception e){
                System.err.println("❌ INVENTORY SERVICE: Failed to update inventory for Order ID: " + orderDTO.getOrderId());

                // gửi tin phản hồi về OrderService để chuyển trạng thái đơn hàng về FAILED
                publisher.publishInventoryFailedEvent(orderDTO);
                e.printStackTrace();
            }
        }
    }
}
