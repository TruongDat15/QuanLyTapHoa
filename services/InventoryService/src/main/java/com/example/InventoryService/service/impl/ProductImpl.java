package com.example.InventoryService.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.example.common.dto.orderdtos.OrderDTO;
import com.example.common.dto.orderdtos.OrderItemDTO;
import com.example.common.dto.paymentdtos.PaymentResultDTO;
import com.example.common.enums.reservedStatus;
import com.example.InventoryService.dto.request.ProductRequest;
import com.example.InventoryService.dto.response.ProductResponse;
import com.example.InventoryService.entity.Brand;
import com.example.InventoryService.entity.Category;
import com.example.InventoryService.entity.InventoryReservation;
import com.example.InventoryService.entity.Product;
import com.example.InventoryService.repository.BrandRepository;
import com.example.InventoryService.repository.CategoryRepository;
import com.example.InventoryService.repository.InventoryReservationRepository;
import com.example.InventoryService.repository.ProductRepository;
import com.example.InventoryService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final InventoryReservationRepository reservationRepository;
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

    @Override
    @Transactional
    public void reserveStock(OrderDTO orderDTO) {
        log.info("Bat dau tam giu ton kho cho don hang: {}", orderDTO.getOrderId());

        for (OrderItemDTO itemDTO : orderDTO.getOrderItemDTOs()) {
            Product product = productRepository.findByBarcode(itemDTO.getBarcode())
                    .orElseThrow(() -> new RuntimeException("Product with barcode " + itemDTO.getBarcode() + " not found"));

            int availableStock = product.getQuantityInStock() - product.getReservedQuantity();
            if (availableStock < itemDTO.getQuantity()) {
                String error = String.format("Tồn kho khả dụng không đủ cho SP %s. Yêu cầu: %d, Khả dụng: %d",
                        itemDTO.getProductName(), itemDTO.getQuantity(), availableStock);
                System.err.println("LỖI GIỮ TẠM KHO: " + error);
                // Ném lỗi để kích hoạt ROLLBACK toàn bộ giao dịch
                throw new RuntimeException(error);
            }


            // Cập nhật reservedQuantity
            product.setReservedQuantity(product.getReservedQuantity() + itemDTO.getQuantity());
            productRepository.save(product);

            // lưu bản ghi chi tiết đơn giữ hàng inventory_reservations
            InventoryReservation inventoryReservation = InventoryReservation.builder()
                    .orderId(orderDTO.getOrderId())
                    .quantity(itemDTO.getQuantity())
                    .createdAt(LocalDateTime.now())
                    .barcode(itemDTO.getBarcode())
                    .status(reservedStatus.PENDING)
                    .productName(itemDTO.getProductName())
                    .build();

            reservationRepository.save(inventoryReservation);

            log.info("Da tam giu {} don vi san pham voi ma vach: {}", itemDTO.getQuantity(), itemDTO.getBarcode());
        }

    }

//    @Override
//    public void handlePaymentCompleted(PaymentResultDTO paymentResultDTO) {
//        // tru kho that va giamr giwx kho
//        UUID orderId = paymentResultDTO.getOrderId();
//
//    }
//
//    @Override
//    public void handlePaymentFailed(PaymentResultDTO paymentResultDTO) {
//        // giamr cai giu tam kho
//    }
    @Override
    @Transactional
    public void handlePaymentCompleted(PaymentResultDTO paymentResultDTO) {

        UUID orderId = paymentResultDTO.getOrderId();
        log.info("Thanh toán thành công – xử lý trừ kho thật cho OrderID: {}", orderId);

        // Lấy tất cả dòng giữ tạm của đơn hàng
        List<InventoryReservation> reservations =
                reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("Không tìm thấy giữ tạm nào cho OrderID {}", orderId);
            return;
        }

        for (InventoryReservation reservation : reservations) {

            // Lấy sản phẩm theo barcode
            Product product = productRepository.findByBarcode(reservation.getBarcode())
                    .orElseThrow(() ->
                            new RuntimeException("Không tìm thấy sản phẩm barcode: " + reservation.getBarcode())
                    );

            int qty = reservation.getQuantity();

            // Trừ kho thật
            if (product.getQuantityInStock() < qty) {
                throw new RuntimeException("Lỗi nghiêm trọng: tồn kho thực tế không đủ khi trừ!");
            }
            product.setQuantityInStock(product.getQuantityInStock() - qty);

            // Giảm reservedQuantity (giải phóng giữ tạm)
            product.setReservedQuantity(product.getReservedQuantity() - qty);

            product.setLastUpdated(LocalDateTime.now());
            productRepository.save(product);

            // Cập nhật trạng thái giữ tạm
            reservation.setStatus(reservedStatus.COMMIT);
            reservationRepository.save(reservation);

            log.info("Đã trừ {} khỏi kho thật & giảm thuộc tính reservedQuantity cho SP: {}",
                    qty, reservation.getBarcode());
        }

        log.info("ĐÃ HOÀN TẤT TRỪ KHO CHO ĐƠN {}", orderId);
    }

    @Override
    @Transactional
    public void handlePaymentFailed(PaymentResultDTO paymentResultDTO) {

        UUID orderId = paymentResultDTO.getOrderId();
        log.info("Thanh toán thất bại / huỷ đơn – giải phóng giữ tạm cho OrderID: {}", orderId);

        List<InventoryReservation> reservations =
                reservationRepository.findByOrderId(orderId);

        if (reservations.isEmpty()) {
            log.warn("Không có giữ tạm nào để giải phóng cho Order {}", orderId);
            return;
        }

        for (InventoryReservation reservation : reservations) {

            Product product = productRepository.findByBarcode(reservation.getBarcode())
                    .orElseThrow(() ->
                            new RuntimeException("Không tìm thấy sản phẩm barcode: " + reservation.getBarcode())
                    );

            int qty = reservation.getQuantity();

            // Chỉ giảm phần giữ tạm → không trừ kho thật
            product.setReservedQuantity(product.getReservedQuantity() - qty);
            product.setLastUpdated(LocalDateTime.now());
            productRepository.save(product);

            // Cập nhật status
            reservation.setStatus(reservedStatus.ROLLBACK);

            reservationRepository.save(reservation);

            log.info("Đã trả lại {} đơn vị giữ tạm cho SP {}", qty, reservation.getBarcode());
        }

        log.info("ĐÃ GIẢI PHÓNG GIỮ TẠM CHO ĐƠN {}", orderId);
    }




}
