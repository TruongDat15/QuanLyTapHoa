package com.example.NotificationService.controller;

import com.example.NotificationService.dto.PaymentNotifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentNotifyController {

    private final SimpMessagingTemplate messagingTemplate;


    // test gửi thông báo
    @PostMapping("/notify")
    public String sendNotify(@RequestBody PaymentNotifyRequest req) {

        System.out.println("✅ PAYMENT SERVICE: Gửi thông báo tới client về đơn hàng " + req.getOrderId());
        // Gửi thông báo tới client theo từng order
        messagingTemplate.convertAndSend("/topic/order/" + req.getOrderId(), req);
        return "Đã gửi thông báo";
    }
}
