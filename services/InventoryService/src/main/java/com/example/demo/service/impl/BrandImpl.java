package com.example.demo.service.impl;

import com.example.demo.entity.Brand;
import com.example.demo.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BrandImpl {

    private final BrandRepository brandRepository;

    public BrandImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }



}
