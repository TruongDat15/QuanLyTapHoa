package com.example.InventoryService.repository;

import com.example.InventoryService.entity.ImportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProduct, Integer> {
}
