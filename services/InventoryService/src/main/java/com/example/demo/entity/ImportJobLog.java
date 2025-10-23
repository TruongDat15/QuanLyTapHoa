package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@Entity
public class ImportJobLog {
    @Id @GeneratedValue
    private Long id;
    private String filename;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
    private int totalRows;
    private int successRows;
    private int failedRows;
    private String errorReportPath;
    private String status; // SUCCESS | FAILED
}