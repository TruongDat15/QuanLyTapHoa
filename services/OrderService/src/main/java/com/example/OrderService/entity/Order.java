package com.example.OrderService.entity;

import com.example.common.enums.OrderStatus;
import com.example.common.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID orderId;

    private String customerId;

    private String cashierId;

    private PaymentMethod paymentMethod;

    private double totalAmount;
    private OrderStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean locked;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

//    @Version
//    private Long version;

    @PrePersist
    @SuppressWarnings("unused")
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    @SuppressWarnings("unused")
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
