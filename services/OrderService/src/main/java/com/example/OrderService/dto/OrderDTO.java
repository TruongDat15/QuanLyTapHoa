package com.example.OrderService.dto;


import com.example.OrderService.entity.Order;
import com.example.OrderService.entity.OrderStatus;
import com.example.OrderService.entity.PaymentMethod;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID orderId;
    private String cashierId;
    private String customerId;
    private Double totalPrice;
    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private List<OrderItemDTO> orderItems;

    // static factory method to create OrderDTO from Order entity
    public static OrderDTO fromEntity(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .cashierId(order.getCashierId())
                .totalPrice(order.getTotalAmount())
                .status(order.getStatus())
                .customerId(order.getCustomerId())
                .paymentMethod(order.getPaymentMethod())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemDTO::fromEntity)
                        .toList())
                .build();
    }
}

