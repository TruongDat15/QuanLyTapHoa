package com.example.demo.entity;

import com.example.common.enums.reservedStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_reservations")
public class InventoryReservation {
    @Id
    @GeneratedValue
    private UUID reservationId;

    private UUID orderId;
    private String productId;
    private Integer quantity;
    private String barcode;
    private String productName;
    private String unit;
    private reservedStatus status;
    private LocalDateTime createdAt;

}
