package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/inventory/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;
    private final com.example.demo.repository.ProductRepository productRepository;

    // lấy tất cả sản phẩm
    @GetMapping
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProducts();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String sayHi(){
        System.out.println("Hello");
        return "Hello Admin";
    }

    @GetMapping("/whoami")
    public String whoAmI() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        return "Authorities: " + auth.getAuthorities();
    }


    @GetMapping("/test")
    public ResponseEntity<?> testHeaders(@RequestHeader Map<String, String> headers) {
        System.out.println("Received headers: " + headers);
        return ResponseEntity.ok(headers);
    }

    // lấy sản phẩm theo barcode
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> getByBarcode(@PathVariable String barcode) {
        Optional<ProductResponse> productResponseOpt = productService.getProductByBarcode(barcode);
        if (productResponseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "barcode", barcode));
        }
        return ResponseEntity.ok(productResponseOpt.get());
    }

    // tạo sản phẩm
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        MultipartFile file = productRequest.getFile(); // có thể null nếu FE không gửi
        ProductResponse createdProduct = productService.createProduct(productRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    // cập nhật sản phẩm
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductRequest productRequest) {
        Optional<ProductResponse> updatedProductOpt = productService.updateProduct(productId, productRequest);
        if (updatedProductOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "productId", productId));
        }
        return ResponseEntity.ok(updatedProductOpt.get());
    }


    // xoá sản phẩm
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




    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Integer id,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = productService.uploadImage(file, id);
        return ResponseEntity.ok(imageUrl);
    }

}
