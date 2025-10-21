package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_PRICE_HISTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

  //  @Column(precision = 18, scale = 2)
    private Double oldPrice;

 //   @Column(precision = 18, scale = 2)
    private Double newPrice;

    private String changedBy;
    private LocalDateTime changedAt;
}
