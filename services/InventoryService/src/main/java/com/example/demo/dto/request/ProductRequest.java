package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


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
    private Integer brandId;
    private Double discount;

    private MultipartFile file;
}