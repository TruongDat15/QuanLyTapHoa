package com.example.OrderService.event;

import java.io.Serializable;

import com.example.OrderService.entity.OrderStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class OrderEventDTO implements Serializable {
    private Integer orderId;
    private String CustomerId;
    private int quantity;
    private OrderStatus status;
}