package com.example.demo.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "IMPORT_PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer importProductId;

    private String code;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String createdBy;

    private Double totalAmount;

    private String status; // pending, received, cancelled...

    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;


    @OneToMany(mappedBy = "importProduct", cascade = CascadeType.ALL)
    private List<ImportDetail> importDetails;
}
