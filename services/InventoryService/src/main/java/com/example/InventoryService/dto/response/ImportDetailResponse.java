package com.example.InventoryService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportDetailResponse {
    private String barcode;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double discount;
    private String unit;
    private Double subtotal;

}