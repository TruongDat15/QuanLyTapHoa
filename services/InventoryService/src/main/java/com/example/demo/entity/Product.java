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

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "unit")
    private String unit;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "cost_of_capital")
    private Double costOfCapital;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImportDetail> importDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPriceHistory> priceHistories;
}
