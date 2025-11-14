package com.example.InventoryService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// DTO trả về thông tin phiếu nhập
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportProductResponse {

    private Integer importProductId;
    private String supplierName;
    private String employeeName;
    private String note;
    private Boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ImportDetailResponse> details;
    private Double totalAmount;
    private String status;
}
