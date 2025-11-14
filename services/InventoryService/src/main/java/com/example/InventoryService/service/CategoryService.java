package com.example.InventoryService.service;

import com.example.InventoryService.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    List<Category> findAll() ;
}
