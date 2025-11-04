package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "unit")
    private String unit;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "cost_of_capital")
    private Double costOfCapital;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock = 0;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String image;


//    private List<ImportDetail> importDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPriceHistory> priceHistories;


    @PrePersist
    protected void onCreate() {
        lastUpdated = LocalDateTime.now();
        if (isActive == null) {
            isActive = true; // mặc định khi tạo mới
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
