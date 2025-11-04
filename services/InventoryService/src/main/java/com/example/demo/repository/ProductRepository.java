package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByBarcode(String barcode);


    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN FETCH p.brand b")
    List<Product> findAllWithCategoryAndBrand();

    Optional<Product> findByBarcode(String barcode);
}
