package com.example.InventoryService.controller;



import com.example.InventoryService.entity.Brand;
import com.example.InventoryService.service.impl.BrandImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/inventory/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandImpl brandservice;

    // ➕ API thêm thương hiệu
    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand createdBrand = brandservice.createBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);

    }

    // 📜 API lấy danh sách thương hiệu
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandservice.getAllBrands();
    }
}

