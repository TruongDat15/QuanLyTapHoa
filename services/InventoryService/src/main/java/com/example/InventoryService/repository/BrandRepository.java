package com.example.InventoryService.repository;

import com.example.InventoryService.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}


