package com.example.OrderService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;



    private String productId;
    private Integer quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
