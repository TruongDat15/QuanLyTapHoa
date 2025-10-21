package com.example.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String productName;
    private String categoryName;
    private String unit;
    private String barcode;
    private Double sellingPrice;
    private Integer quantityInStock;
    private LocalDateTime lastUpdated;
}
