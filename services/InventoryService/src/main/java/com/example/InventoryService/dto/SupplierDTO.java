package com.example.InventoryService.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierDTO {
    private Integer supplierId;
    private String supplierName;
    private String phone;
    private String email;
    private String address;
    private String note;
}

