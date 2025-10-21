package com.example.demo.entity;



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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "import_product_id")
    private ImportProduct importProduct;

    private Integer quantity;

 //   @Column(precision = 18, scale = 2)
    private Double importPrice;

   // @Column(precision = 18, scale = 2)
    private Double subtotal;
}
