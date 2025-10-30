package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportDetailRequest {
    private String barcode;     // Barcode sản phẩm
    private String productName; // Tên sản phẩm (nếu sản phẩm chưa tồn tại)
    private Integer quantity;   // Số lượng nhập
    private Double price;
    private Double discount;
    private String unit;

}