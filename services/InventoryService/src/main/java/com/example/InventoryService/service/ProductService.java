package com.example.InventoryService.service;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.InventoryService.dto.request.ProductRequest;
import com.example.InventoryService.dto.response.ProductResponse;
import com.example.common.dto.paymentdtos.PaymentResultDTO;
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

    void reserveStock(OrderDTO orderDTO);

    void handlePaymentCompleted(PaymentResultDTO paymentResultDTO);

    void handlePaymentFailed(PaymentResultDTO paymentResultDTO);
}
