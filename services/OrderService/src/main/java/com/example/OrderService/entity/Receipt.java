package com.example.OrderService.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
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
public class Receipt {

    @Id
    @GeneratedValue
    private UUID receiptId;
    private String receiptNumber;
    private UUID orderId;
    private String content;
    private String issuedAt;
    private String taxCode;
    private String storeInfo;

}
