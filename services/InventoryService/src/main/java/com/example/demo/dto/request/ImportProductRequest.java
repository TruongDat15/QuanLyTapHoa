package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO tạo/ cập nhật phiếu nhập
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportProductRequest {

    private Integer supplierId;          // Nhà cung cấp
    private Integer employeeId;          // Người nhập
    private String note;                 // Ghi chú
    private Boolean complete;            // true: hoàn thành, false: tạm
    private List<ImportDetailRequest> details; // Các dòng sản phẩm nhập
}