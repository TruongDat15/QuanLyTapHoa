package com.example.NotificationService.event;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.example.common.constrants.RabbitConstants.NOTIFICATION_QUEUE;

@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final SimpMessagingTemplate template;

    @RabbitListener(queues = NOTIFICATION_QUEUE)
    public void handlePaymentNotification(String message) {
        template.convertAndSend("/topic/notifications", message);
    }
}
