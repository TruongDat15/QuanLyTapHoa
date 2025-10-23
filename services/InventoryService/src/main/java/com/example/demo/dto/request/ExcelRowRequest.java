package com.example.demo.dto.request;


import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExcelRowRequest {
    private Integer rowNumber;
    private String productBarCode;
    private String productName;
    private String productUnit;
    private Integer quantity;
    private Double unitPrice;
}