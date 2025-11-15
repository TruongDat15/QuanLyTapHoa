package com.example.common.dto.paymentdtos;

import com.example.common.enums.PaymentMethod;
import com.example.common.enums.PaymentStatus;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultDTO {
    private UUID paymentId;
    private UUID orderId;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private double amount;
    private String transactionId;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String paymentReference;
    private String paymentNote;
}
