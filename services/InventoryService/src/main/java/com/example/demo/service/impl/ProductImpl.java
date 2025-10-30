package com.example.demo.service.impl;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                        .unit(product.getUnit())
                        .barcode(product.getBarcode())
                        .sellingPrice(product.getSellingPrice())
                        .quantityInStock(product.getQuantityInStock())
                        .lastUpdated(product.getLastUpdated())
                        .isActive(product.getIsActive())
                        .brandName(product.getBrand() != null ? product.getBrand().getBrandName() : null)
                        .costOfCapital(product.getCostOfCapital())
                        .discount(product.getDiscount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllActiveProducts() {
        return List.of();
    }

    @Override
    public Optional<ProductResponse> getProductByBarcode(String barcode) {

        Optional<Product> productOpt = productRepository.findByBarcode(barcode);
        return productOpt.map(product -> ProductResponse.builder()
                .productName(product.getProductName())
                .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                .unit(product.getUnit())
                .barcode(product.getBarcode())
                .sellingPrice(product.getSellingPrice())
                .quantityInStock(product.getQuantityInStock())
                .build());
    }



    @Override
    public Optional<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return Optional.empty();
        }

        Product product = productOpt.get();

        Category category = null;
        if (productRequest.getCategoryId() != null) {
            category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category ID"));
        }

        String barcode = productRequest.getBarcode();
        if (barcode != null && !barcode.equals(product.getBarcode()) && productRepository.findByBarcode(barcode).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barcode already exists");
        }

        product.setProductName(productRequest.getProductName());
        product.setCategory(category);
        product.setUnit(productRequest.getUnit());
        product.setBarcode(productRequest.getBarcode());
        product.setSellingPrice(productRequest.getSellingPrice());
        product.setQuantityInStock(productRequest.getQuantityInStock());
        product.setLastUpdated(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        ProductResponse productResponse = ProductResponse.builder()
                .productName(updatedProduct.getProductName())
                .categoryName(updatedProduct.getCategory() != null ? updatedProduct.getCategory().getCategoryName() : null)
                .unit(updatedProduct.getUnit())
                .barcode(updatedProduct.getBarcode())
                .sellingPrice(updatedProduct.getSellingPrice())
                .quantityInStock(updatedProduct.getQuantityInStock())
                .build();

        return Optional.of(productResponse);
    }




//    @Override
//    public void deleteProduct(Integer productId) {
//        if (!productRepository.existsById(productId)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
//        }
//
//        productRepository.deleteById(productId);
//    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Soft delete: chỉ set isActive = false và cập nhật lastUpdated
        product.setIsActive(false);
        product.setLastUpdated(LocalDateTime.now());
        productRepository.save(product);
    }


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = null;
        if (productRequest.getCategoryId() != null) {
            category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category ID"));
        }

        String barcode = productRequest.getBarcode();
        if (barcode != null && productRepository.findByBarcode(barcode).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barcode already exists");
        }

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .category(category)
                .unit(productRequest.getUnit())
                .barcode(productRequest.getBarcode())
                .sellingPrice(productRequest.getSellingPrice())
                .quantityInStock(productRequest.getQuantityInStock())
                .lastUpdated(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponse.builder()
                .productName(savedProduct.getProductName())
                .categoryName(savedProduct.getCategory() != null ? savedProduct.getCategory().getCategoryName() : null)
                .unit(savedProduct.getUnit())
                .barcode(savedProduct.getBarcode())
                .sellingPrice(savedProduct.getSellingPrice())
                .quantityInStock(savedProduct.getQuantityInStock())
                .build();
    }


}
