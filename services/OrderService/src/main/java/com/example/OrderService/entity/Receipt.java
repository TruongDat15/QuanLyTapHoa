package com.example.OrderService.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    private Integer receiptId;
    private String receiptNumber;
    private Integer orderId;
    private String content;
    private String issuedAt;
    private String taxCode;
    private String storeInfo;


}
