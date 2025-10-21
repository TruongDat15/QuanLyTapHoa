package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private String productName;
    private String unit;
    private String barcode;
    private Double sellingPrice;
    private Double costOfCapital;
    private Integer quantityInStock;
    private Boolean isActive;
    private Integer categoryId;

}