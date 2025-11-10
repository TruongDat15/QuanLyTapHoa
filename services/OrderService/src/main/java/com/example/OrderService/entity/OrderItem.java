package com.example.OrderService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
