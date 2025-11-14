package com.example.InventoryService.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "IMPORT_DETAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String barcode;

    @ManyToOne
    @JoinColumn(name = "import_product_id")
    private ImportProduct importProduct;

    private Integer quantity;
    private String unit;
 //   @Column(precision = 18, scale = 2)
    private Double importPrice;

   // @Column(precision = 18, scale = 2)
    private Double subtotal;

    private Double discount;
}
