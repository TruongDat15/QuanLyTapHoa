package com.example.demo.enums;

public enum ImportStatus {
    PENDING,        // Đang chờ xử lý
    IN_PROGRESS,    // Đang tiến hành
    COMPLETED,      // Hoàn thành
    COMPLETED_WITH_ERRORS, // Hoàn thành với lỗi
    FAILED          // Thất bại
}
