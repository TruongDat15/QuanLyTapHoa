package com.example.PaymentService.event.publisher;


import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.dto.paymentdtos.PaymentResultDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.common.constrants.RabbitConstants.*;

@Service
public class PaymentPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishPaymentCompletedEvent(PaymentResultDTO paymentResultDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                PAYMENT_COMPLETED_KEY,
                paymentResultDTO
        );
        System.out.println(" Gui sự kiện thanh toán thành công");
    }

    public void publishPaymentFailedEvent(OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(
                ORDER_EXCHANGE,
                INVENTORY_RESERVED_KEY,
                orderDTO
        );
        System.out.println(" Gui sự kiện giữ tạm kho thành công");
    }
}