package com.example.InventoryService.dto.request;


import lombok.*;

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