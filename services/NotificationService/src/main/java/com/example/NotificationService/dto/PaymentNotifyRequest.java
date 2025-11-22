package com.example.NotificationService.dto;
import lombok.Data;

@Data
public class PaymentNotifyRequest {
    private String orderId;
    private String message;
    private String status;

}
