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

    @PostMapping("/notify")
    public String sendNotify(@RequestBody PaymentNotifyRequest req) {
        // Gửi thông báo tới client theo từng order
        messagingTemplate.convertAndSend("/topic/order/" + req.getOrderId(), req);
        return "Đã gửi thông báo";
    }
}
