package com.example.NotificationService.event;


import com.example.NotificationService.dto.PaymentClientDTO;
import com.example.NotificationService.dto.PaymentNotifyRequest;
import com.example.NotificationService.util.VietQRUtil;
import com.example.common.dto.orderdtos.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.example.common.constrants.RabbitConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final SimpMessagingTemplate messagingTemplate;


    // lắng nghe sự kiện tu order gửi về client phương thức thanh toán
    @RabbitListener(queues = NOTIFICATION_QUEUE)
    public void handleStartSale(OrderDTO orderDTO, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        log.info("NHẬN SỰ KỆN {} ", routingKey);
//
        if(routingKey.equals(ORDER_PENDING_KEY)) {
            // caabf theem kiemer tra ddieeuf kien don hang co dc thanh toan ko , neu khong thi gui ve cai khac
            System.out.println("🔔 1.5 NOTIFICATION SERVICE: Nghe đủ tồn kho và gửi thanh toán: ");
            PaymentClientDTO req = new PaymentClientDTO();
            req.setOrderId(String.valueOf(orderDTO.getOrderId()));
            req.setTotalPrice(orderDTO.getTotalPrice());
            req.setPaymentStatus(orderDTO.getPaymentStatus());
            req.setPaymentMethod(orderDTO.getPaymentMethod());
            req.setOrderStatus(orderDTO.getStatus());


            String qrMessage = VietQRUtil.createVietQRLink(
                    "MB",
                    "0393806942",
                    "print",
                    orderDTO.getTotalPrice(),
                    "Nap tien cho don hang " + orderDTO.getOrderId(),
                    "Trương Đỉnh Đạt"
            );
            req.setQrcodeUrl(qrMessage);
            messagingTemplate.convertAndSend("/topic/order/" + req.getOrderId(), req);
            log.info("Gui ma QR: {}", qrMessage);
            log.info(" GỬI THÔNG BÁO ĐẾN CLIENT {}", req.getOrderId());
            log.info("Trạng thái thanh toán : {}", req.getPaymentStatus());
            System.out.println("✅ 1.5.1 NOTIFICATION SERVICE: Sent payment notification to client for order " + req);
        }

        // neu nhan dc don thanh cong thi gui thong bao ve client
        if(routingKey.equals(ORDER_COMPLETED_KEY)) {
            System.out.println("🔔 1.7 NOTIFICATION SERVICE: Nghe thanh toán thành công và gửi thông báo: ");
            PaymentNotifyRequest notifyRequest = new PaymentNotifyRequest();
            notifyRequest.setOrderId(String.valueOf(orderDTO.getOrderId()));
            notifyRequest.setMessage("Thanh toán thành công cho đơn hàng " + orderDTO.getOrderId());
            messagingTemplate.convertAndSend("/topic/notification/" + notifyRequest.getOrderId(), notifyRequest);
            System.out.println("✅ 1.7.1 NOTIFICATION SERVICE: Sent payment success notification to client for order " + notifyRequest);
        }

        if(routingKey.equals(PAYMENT_COMPLETED_KEY)) {
            // gui ve client thong bao thanh toan thanh cong
            System.out.println("🔔 1.9 NOTIFICATION SERVICE: Nghe thanh toán hoàn tất và gửi thông báo: ");
            PaymentNotifyRequest notifyRequest = new PaymentNotifyRequest();
            notifyRequest.setOrderId(String.valueOf(orderDTO.getOrderId()));
            notifyRequest.setMessage("Thanh toán hoàn tất cho đơn hàng " + orderDTO.getOrderId());
            messagingTemplate.convertAndSend("/topic/notification/" + notifyRequest.getOrderId(), notifyRequest);
            System.out.println("✅ 1.9.1 NOTIFICATION SERVICE: Sent payment completed notification to client for order " + notifyRequest);
        }

    }
}
