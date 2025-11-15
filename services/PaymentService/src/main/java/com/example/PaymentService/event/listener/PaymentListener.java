package com.example.PaymentService.event.listener;


import com.example.PaymentService.entity.Payment;
import com.example.PaymentService.repository.PaymentRepository;
import com.example.PaymentService.service.PaymentService;
import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.example.common.constrants.RabbitConstants.PAYMENT_QUEUE;
import static com.example.common.constrants.RabbitConstants.PAYMENT_REQUESTED_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

    private final PaymentService paymentService;


    @RabbitListener(queues = PAYMENT_QUEUE)
    public void handlePaymentCreated(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        log.info("NHẬN SỰ KỆN {} ", routingKey);
        if(PAYMENT_REQUESTED_KEY.equals(routingKey)){
            try{
                // gọi service xủ lí logic thanh toán.
                Payment payment = paymentService.processPayment(orderDTO);
                log.info("Đang xử lý logic thanh toán");

                if(payment.getStatus() == PaymentStatus.COMPLETED){
                    // phát sự kiện thành công và yêu cầu cập nhật trạng thái đơn hàng, truwf kho.
                    paymentService.publishPaymentCompleted(payment);
                    log.info(" ỬI SỰ KIỆN THANH TON THÀNH CÔNG");
                }

            }catch (Exception e){
                throw new RuntimeException("Failed to update payment for Order ID: " + orderDTO.getOrderId());
            }
        }
        log.info(" Nhận được tin nhăns rồi");
    }
}
