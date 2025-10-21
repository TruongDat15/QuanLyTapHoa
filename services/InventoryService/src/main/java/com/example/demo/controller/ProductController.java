package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final com.example.demo.repository.ProductRepository productRepository;


    @GetMapping
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProducts();
    }
    @GetMapping("/hello")
    public String sayHi(){
        System.out.println("Hello");
        return "Hello hello";
    }


    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> getByBarcode(@PathVariable String barcode) {
        Optional<ProductResponse> productResponseOpt = productService.getProductByBarcode(barcode);
        if (productResponseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "barcode", barcode));
        }
        return ResponseEntity.ok(productResponseOpt.get());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductRequest productRequest) {
        Optional<ProductResponse> updatedProductOpt = productService.updateProduct(productId, productRequest);
        if (updatedProductOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "productId", productId));
        }
        return ResponseEntity.ok(updatedProductOpt.get());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "productId", productId));
        }
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
