package com.example.demo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final Cloudinary cloudinary;

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
                        .image(product.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllActiveProducts() {
        return List.of();
    }

    @Override
    public List<ProductResponse> getAllWithCategoryAndBrand() {
        List<Product> products = productRepository.findAllWithCategoryAndBrand();
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
                        .image(product.getImage())
                        .build())
                .collect(Collectors.toList());
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
        Brand brand = null;
        if (productRequest.getBrandId() != null) {
            brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid brand ID"));

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
        product.setCostOfCapital(productRequest.getCostOfCapital());
        product.setDiscount(productRequest.getDiscount());
        product.setBrand(brand);

        product.setLastUpdated(LocalDateTime.now());
        product.setIsActive(productRequest.getIsActive());

        Product updatedProduct = productRepository.save(product);

        ProductResponse productResponse = ProductResponse.builder()
                .productName(updatedProduct.getProductName())
                .categoryName(updatedProduct.getCategory() != null ? updatedProduct.getCategory().getCategoryName() : null)
                .unit(updatedProduct.getUnit())
                .brandName(updatedProduct.getBrand() != null ? updatedProduct.getBrand().getBrandName() : null)
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
    public ProductResponse createProduct(ProductRequest productRequest, MultipartFile file) throws IOException {
        Category category = null;
        if (productRequest.getCategoryId() != null) {
            category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category ID"));
        }

        // Check brand
        Brand brand = null;
        if (productRequest.getBrandId() != null) {
            brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid brand ID"));
        }

        String barcode = productRequest.getBarcode();
        if (barcode != null && productRepository.findByBarcode(barcode).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barcode already exists");
        }

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .category(category)
                .brand(brand)
                .unit(productRequest.getUnit())
                .barcode(productRequest.getBarcode())
                .sellingPrice(productRequest.getSellingPrice())
                .quantityInStock(productRequest.getQuantityInStock())
                .costOfCapital(productRequest.getCostOfCapital())
                .discount(productRequest.getDiscount())
                .isActive(true)
                .lastUpdated(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);


        if (file != null && !file.isEmpty()) {
            String imageUrl = uploadImage(file, savedProduct.getProductId()); // chưa có productId, Cloudinary folder có thể dùng tên tạm
            product.setImage(imageUrl);
            productRepository.save(product);
        } else {
            product.setImage("https://res.cloudinary.com/dxr2k9oz2/image/upload/v1761802800/defautImage_rozrkb.png"); // FE không gửi ảnh → để null
            productRepository.save(product);
        }

        return ProductResponse.builder()
                .productName(savedProduct.getProductName())
                .categoryName(savedProduct.getCategory() != null ? savedProduct.getCategory().getCategoryName() : null)
                .unit(savedProduct.getUnit())
                .brandName(savedProduct.getBrand() != null ? savedProduct.getBrand().getBrandName() : null)
                .barcode(savedProduct.getBarcode())
                .sellingPrice(savedProduct.getSellingPrice())
                .quantityInStock(savedProduct.getQuantityInStock())
                .lastUpdated(savedProduct.getLastUpdated())
                .isActive(savedProduct.getIsActive())
                .costOfCapital(savedProduct.getCostOfCapital())
                .discount(savedProduct.getDiscount())
                .image(savedProduct.getImage())
                .build();
    }

    @Override
    public String uploadImage(MultipartFile file, Integer productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Chỉ hỗ trợ file ảnh!");
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "products/" + productId));  // Folder theo ID

        // Lấy URL
        String imageUrl = (String) uploadResult.get("secure_url");

        // Cập nhật Product
        product.setImage(imageUrl);

        // Lưu DB
        productRepository.save(product);

        return imageUrl;
    }


}
