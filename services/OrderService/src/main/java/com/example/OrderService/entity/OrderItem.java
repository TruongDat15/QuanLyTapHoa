package com.example.OrderService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID orderItemId;

    private String productName;
    private String barcode;
    private String unit;

    private String productId;
    private Integer quantity;
    private double price;
    private String note;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
