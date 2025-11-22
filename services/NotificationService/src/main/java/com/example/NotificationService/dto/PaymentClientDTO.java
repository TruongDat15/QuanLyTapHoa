package com.example.NotificationService.dto;


import com.example.common.enums.OrderStatus;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentClientDTO {
    private String orderId;
    private String userId;
    private double totalPrice;
    private String paymentId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String qrcodeUrl;
    private String message;
}
