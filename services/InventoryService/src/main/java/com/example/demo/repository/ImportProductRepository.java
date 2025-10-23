package com.example.demo.repository;

import com.example.demo.entity.ImportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProduct, Integer> {
}
