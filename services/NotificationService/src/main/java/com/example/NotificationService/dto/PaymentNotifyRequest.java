package com.example.NotificationService.dto;
import com.example.common.enums.OrderStatus;
import lombok.Data;

@Data
public class PaymentNotifyRequest {
    private String orderId;
    private String message;
    private OrderStatus status;

}
