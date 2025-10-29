package com.example.common.dto.customerdtos;

import lombok.Data;

@Data
public class RefundPointRequest {
    private Long customerId;
    private Integer pointsToRefund;
    private String orderId;
}
