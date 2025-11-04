package com.example.demo.service;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllActiveProducts();
    List<ProductResponse> getAllWithCategoryAndBrand();
    Optional<ProductResponse> getProductByBarcode(String barcode);
    Optional<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);
    ProductResponse createProduct(ProductRequest productRequest, MultipartFile file) throws IOException;

    String uploadImage (MultipartFile file, Integer productId) throws IOException;
}
