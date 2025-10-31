package com.example.demo.controller;



import com.example.demo.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByBrandName(String brandName);
}

@RestController
@RequestMapping("/api/inventory/brand")

public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    // ➕ API thêm thương hiệu
    @PostMapping
    public Brand addBrand(@RequestBody Brand brand) {
        if (brandRepository.existsByBrandName(brand.getBrandName())) {
            throw new RuntimeException("Tên thương hiệu đã tồn tại");
        }
        return brandRepository.save(brand);
    }

    // 📜 API lấy danh sách thương hiệu
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}

