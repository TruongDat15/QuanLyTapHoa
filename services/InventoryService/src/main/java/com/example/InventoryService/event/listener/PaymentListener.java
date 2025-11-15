package com.example.InventoryService.event.listener;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.InventoryService.event.publisher.InventoryPublisher;
import com.example.InventoryService.service.ProductService;
import com.example.common.dto.paymentdtos.PaymentResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Header;
import static com.example.common.constrants.RabbitConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final ProductService productService;
    private final InventoryPublisher publisher;

    @RabbitListener(queues = INVENTORY_PAYMENT_QUEUE)
    public void handlePaymentEvent(PaymentResultDTO paymentResultDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        log.info("Nhận được tin thanh toán {}", routingKey );

        switch (routingKey){
            case PAYMENT_COMPLETED_KEY:
                productService.handlePaymentCompleted(paymentResultDTO);
                System.out.println("Thanh toán thành công");
                break;
            case PAYMENT_FAILED_KEY:
                productService.handlePaymentFailed(paymentResultDTO);
                System.out.println("Thanh toán thất bại");
                break;
            default:
                break;
        }



    }
}
