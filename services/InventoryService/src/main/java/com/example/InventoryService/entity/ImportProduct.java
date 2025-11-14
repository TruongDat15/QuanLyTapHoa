package com.example.InventoryService.entity;



import com.example.InventoryService.enums.ImportStatus;
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

    private String note;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String createdBy;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ImportStatus status; // draft, complete

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "importProduct", cascade = CascadeType.ALL)
    private List<ImportDetail> importDetails;
}
