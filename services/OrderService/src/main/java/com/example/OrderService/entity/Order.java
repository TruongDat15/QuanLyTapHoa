package com.example.OrderService.entity;

import com.example.common.enums.OrderStatus;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Builder
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

    @Default
    private PaymentStatus paymentStatus = PaymentStatus.DRAFT;

    private double totalAmount;

    private OrderStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean locked;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private String orderNote;

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
