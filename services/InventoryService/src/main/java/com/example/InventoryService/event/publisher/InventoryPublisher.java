package com.example.InventoryService.event.publisher;


import static com.example.common.constrants.RabbitConstants.*;
import com.example.common.dto.orderdtos.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryPublisher {

    private final RabbitTemplate rabbitTemplate;

    public InventoryPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishInventoryReservedEvent(OrderDTO orderDTO){
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                INVENTORY_RESERVED_KEY,
                orderDTO
        );
        System.out.println("✅ 1.3 Gui sự kiện giữ tạm kho thành công cho đơn hàng: " + orderDTO.getOrderId());
    }

    public void publishInventoryFailedEvent(OrderDTO orderDTO){

        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                INVENTORY_REJECTED_KEY,
                orderDTO
        );

        // nên gửi về thiếu cái gì nữa
        System.out.println("⛔   Khong du ton kho, thay doi hoac dois");
    }


    // bỏ

    public void publishPaymentEvent(OrderDTO orderDTO){
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                PAYMENT_REQUESTED_KEY,
                orderDTO
        );
        System.out.println("Gui su kien thanh toan thanh cong");
    }
}
