
package com.example.InventoryService.service.impl;

import com.example.InventoryService.dto.request.ImportDetailRequest;
import com.example.InventoryService.dto.request.ImportProductRequest;
import com.example.InventoryService.dto.response.ImportDetailResponse;
import com.example.InventoryService.dto.response.ImportProductResponse;
import com.example.InventoryService.entity.ImportDetail;
import com.example.InventoryService.entity.ImportProduct;
import com.example.InventoryService.entity.Product;
import com.example.InventoryService.entity.Supplier;
import com.example.InventoryService.enums.ImportStatus;
import com.example.InventoryService.repository.*;
import com.example.InventoryService.service.ImportProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportProductImpl implements ImportProductService {

    private final ProductRepository productRepository;
    private final ImportDetailRepository importDetailRepository;
    private final ImportProductRepository importProductRepository;
    private final SupplierRepository supplierRepository;



    @Transactional
    @Override
    public ImportProductResponse createImportProduct(ImportProductRequest request, String username) {
        // lấy nhà cung cấp
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhà cung cấp với ID: " + request.getSupplierId()));

        Double totalAmount = (double) 0;

        // taọ phiếu nhập
        ImportProduct importProduct = ImportProduct.builder()
                .supplier(supplier)
                .createdBy(username)             // them tạm
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(request.getComplete() ? ImportStatus.COMPLETED : ImportStatus.DRAFT)
                .note(request.getNote())
                .build();

        importProductRepository.save(importProduct);

        // lưu từng chi tiết sản phẩm
        for (ImportDetailRequest detailRequest : request.getDetails()) {

            Product product = productRepository.findByBarcode(detailRequest.getBarcode())
                    .orElseGet(() -> {
                        Product p = Product.builder()
                                .productName(detailRequest.getProductName())
                                .barcode(detailRequest.getBarcode())
                                .unit(detailRequest.getUnit())
                                .discount(detailRequest.getDiscount())
                                .costOfCapital(detailRequest.getPrice())
                                .quantityInStock(0)
                                .build();
                        return productRepository.save(p);
                    });
            // neu true thi tang ton kho
            if (request.getComplete()){
                product.setQuantityInStock(product.getQuantityInStock() + detailRequest.getQuantity());
                productRepository.save(product);
            }

            ImportDetail detail = ImportDetail.builder()
                    .importProduct(importProduct)
                    .quantity(detailRequest.getQuantity())
                    .importPrice(detailRequest.getPrice())
                    .discount(detailRequest.getDiscount())
                    .barcode(detailRequest.getBarcode())
                    .unit(detailRequest.getUnit())
                    .subtotal(detailRequest.getQuantity() * detailRequest.getPrice())
                    .build();
            importDetailRepository.save(detail);
            totalAmount += detail.getSubtotal();
        }
        importProduct.setTotalAmount(totalAmount);
        importProductRepository.save(importProduct);

        return ImportProductResponse.builder()
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public ImportProductResponse updateImportProduct(Integer importProductId, ImportProductRequest request, boolean complete) {
        return null;
    }

    @Override
    public List<ImportProductResponse> getAll(Integer importProductId) {
        return List.of();
    }


    @Transactional(readOnly = true)
    @Override
    public List<ImportProductResponse> getAllImportProducts() {
        List<ImportProduct> importProducts = importProductRepository.findAll();

        return importProducts.stream().map(importProduct -> {
            List<ImportDetailResponse> details = importProduct.getImportDetails().stream()
                    .map(detail -> ImportDetailResponse.builder()
                            .barcode(detail.getBarcode())
                            .productName(productRepository.findByBarcode(detail.getBarcode()).orElse(null).getProductName())
                            .unit(detail.getUnit())
                            .quantity(detail.getQuantity())
                            .price(detail.getImportPrice())
                            .discount(detail.getDiscount())
                            .subtotal(detail.getSubtotal())
                            .build())
                    .collect(Collectors.toList());

            return ImportProductResponse.builder()
                    .importProductId(importProduct.getImportProductId())
                    .supplierName(importProduct.getSupplier().getSupplierName())
                    .employeeName(importProduct.getCreatedBy())   // them tạm
                    .createdAt(importProduct.getCreatedAt())
                    .updatedAt(importProduct.getUpdatedAt())
                    .status(importProduct.getStatus().name())
                    .note(importProduct.getNote())
                    .details(details)
                    .totalAmount(importProduct.getTotalAmount())
                    .build();
        }).collect(Collectors.toList());
    }



//    @Transactional
//    @Override
//    public ImportResultResponse processExcelRow(MultipartFile file, Integer supplierId, Boolean isSave) throws IOException {
//        List<String> errors = new ArrayList<>();
//        int successCount = 0;
//        int failCount = 0;
//        // Đọc dữ liệu Excel
//        List<ExcelRowRequest> rows = ExcelHelper.parseExcel(file, errors);
//        log.info("Đã đọc {} dòng từ file Excel", rows.size());
//
//        // Nếu lỗi định dạng khi đọc Excel → trả về luôn
//        if (!errors.isEmpty()) {
//            return new ImportResultResponse(
//                    false,
//                    rows.size(),
//                    0,
//                    errors.size(),
//                    "File Excel có lỗi định dạng, vui lòng kiểm tra lại.",
//                    errors
//            );
//        }
//
//        // Lấy nhà cung cấp
//        var supplier = supplierRepository.findById(supplierId).orElse(null);
//        if (supplier == null) {
//            errors.add("Không tìm thấy nhà cung cấp với ID: " + supplierId);
//            return new ImportResultResponse(false, 0, 0, 1, "Nhà cung cấp không hợp lệ.", errors);
//        }
//
//        try {
//            // Tạo phiếu nhập hàng
//            LocalDateTime now = LocalDateTime.now();
//
//            ImportProduct importProduct = new ImportProduct();
//            importProduct.setSupplier(supplier);
//       //     importProduct.setReceivedAt(now);
////            importProduct.setStatus(ImportStatus.PENDING);
//            importProduct = importProductRepository.save(importProduct);
//
//            // Xử lý từng dòng trong Excel
//            for (ExcelRowRequest row : rows) {
//                try {
//                    if (row.getProductBarCode() == null || row.getProductName() == null) {
//                        throw new IllegalArgumentException("Thiếu mã sản phẩm hoặc tên sản phẩm.");
//                    }
//
//                    Product product = productRepository.findByBarcode(row.getProductBarCode()).orElse(null);
//
//                    if (product == null) {
//                        // Sản phẩm chưa tồn tại → tạo mới
//                        product = new Product();
//                        product.setBarcode(row.getProductBarCode());
//                        product.setProductName(row.getProductName());
//                        product.setUnit(row.getProductUnit());
//                        product.setQuantityInStock(row.getQuantity());
//                        product.setLastUpdated(now);
//                        product.setCostOfCapital(row.getUnitPrice());
//                        product = productRepository.save(product);
//
//                        log.info("Thêm mới sản phẩm: {} - {}", row.getProductBarCode(), row.getProductName());
//                    } else {
//                        // Đã tồn tại → cập nhật số lượng
//                        int currentQty = product.getQuantityInStock() == null ? 0 : product.getQuantityInStock();
//                        product.setQuantityInStock(currentQty + row.getQuantity());
//                        product.setLastUpdated(now);
//                        productRepository.save(product);
//
//                        log.info("Cập nhật tồn kho sản phẩm: {} - số lượng mới: {}", row.getProductBarCode(), product.getQuantityInStock());
//                    }
//
//                    // Lưu chi tiết nhập
//                    ImportDetail detail = new ImportDetail();
//                    detail.setImportProduct(importProduct);
//           //         detail.setProduct(product);
//                    detail.setQuantity(row.getQuantity());
//                    detail.setImportPrice(row.getUnitPrice());
//                    detail.setUnit(row.getProductUnit());
//                    importDetailRepository.save(detail);
//
//                    successCount++;
//
//                } catch (Exception e) {
//                    failCount++;
//                    String msg = "Lỗi tại dòng " + row.getRowNumber() + ": " + e.getMessage();
//                    log.error(msg);
//                    errors.add(msg);
//                }
//            }
//
//            // Cập nhật trạng thái phiếu nhập
//            importProduct.setStatus(failCount == 0 ? ImportStatus.COMPLETED : ImportStatus.DRAFT);
//            importProductRepository.save(importProduct);
//
//            String message = failCount == 0
//                    ? "Nhập hàng thành công toàn bộ."
//                    : "Nhập hàng hoàn tất, có " + failCount + " dòng lỗi.";
//
//            return new ImportResultResponse(true, rows.size(), successCount, failCount, message, errors);
//
//        } catch (Exception e) {
//            log.error("Lỗi nghiêm trọng trong quá trình nhập hàng", e);
//            throw e; // rollback toàn bộ
//        }
//    }



}
