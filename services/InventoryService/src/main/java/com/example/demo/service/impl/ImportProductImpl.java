
package com.example.demo.service.impl;

import com.example.demo.dto.request.ExcelRowRequest;
import com.example.demo.dto.response.ImportResultResponse;
import com.example.demo.entity.ImportDetail;
import com.example.demo.entity.ImportProduct;
import com.example.demo.entity.Product;
import com.example.demo.helper.ExcelHelper;
import com.example.demo.repository.*;
import com.example.demo.service.ImportProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportProductImpl implements ImportProductService {

    private final ProductRepository productRepository;
    private final ImportDetailRepository importDetailRepository;
    private final ImportProductRepository importProductRepository;
    private final ImportJobLogRepository importJobLogRepository;
    private final SupplierRepository supplierRepository;

    private final Path storageLocation = Path.of("uploads"); // thư mục lưu tệp nếu cần

    @Transactional
    @Override
    public ImportResultResponse processExcelRow(MultipartFile file, Integer supplierId) throws IOException {
        List<String> errors = new ArrayList<>();
        int successCount = 0;
        int failCount = 0;

        // Đọc dữ liệu Excel
        List<ExcelRowRequest> rows = ExcelHelper.parseExcel(file, errors);
        log.info("Đã đọc {} dòng từ file Excel", rows.size());

        // Nếu lỗi định dạng khi đọc Excel → trả về luôn
        if (!errors.isEmpty()) {
            return new ImportResultResponse(
                    false,
                    rows.size(),
                    0,
                    errors.size(),
                    "File Excel có lỗi định dạng, vui lòng kiểm tra lại.",
                    errors
            );
        }

        // Lấy nhà cung cấp
        var supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier == null) {
            errors.add("Không tìm thấy nhà cung cấp với ID: " + supplierId);
            return new ImportResultResponse(false, 0, 0, 1, "Nhà cung cấp không hợp lệ.", errors);
        }

        try {
            // Tạo phiếu nhập hàng
            LocalDateTime now = LocalDateTime.now();

            ImportProduct importProduct = new ImportProduct();
            importProduct.setSupplier(supplier);
            importProduct.setReceivedAt(now);
            importProduct.setStatus("Pending");
            importProduct = importProductRepository.save(importProduct);

            // Xử lý từng dòng trong Excel
            for (ExcelRowRequest row : rows) {
                try {
                    if (row.getProductBarCode() == null || row.getProductName() == null) {
                        throw new IllegalArgumentException("Thiếu mã sản phẩm hoặc tên sản phẩm.");
                    }

                    Product product = productRepository.findByBarcode(row.getProductBarCode()).orElse(null);

                    if (product == null) {
                        // Sản phẩm chưa tồn tại → tạo mới
                        product = new Product();
                        product.setBarcode(row.getProductBarCode());
                        product.setProductName(row.getProductName());
                        product.setUnit(row.getProductUnit());
                        product.setQuantityInStock(row.getQuantity());
                        product.setLastUpdated(now);
                        product.setCostOfCapital(row.getUnitPrice());
                        product = productRepository.save(product);

                        log.info("Thêm mới sản phẩm: {} - {}", row.getProductBarCode(), row.getProductName());
                    } else {
                        // Đã tồn tại → cập nhật số lượng
                        int currentQty = product.getQuantityInStock() == null ? 0 : product.getQuantityInStock();
                        product.setQuantityInStock(currentQty + row.getQuantity());
                        product.setLastUpdated(now);
                        productRepository.save(product);

                        log.info("Cập nhật tồn kho sản phẩm: {} - số lượng mới: {}", row.getProductBarCode(), product.getQuantityInStock());
                    }

                    // Lưu chi tiết nhập
                    ImportDetail detail = new ImportDetail();
                    detail.setImportProduct(importProduct);
                    detail.setProduct(product);
                    detail.setQuantity(row.getQuantity());
                    detail.setImportPrice(row.getUnitPrice());
                    detail.setUnit(row.getProductUnit());
                    importDetailRepository.save(detail);

                    successCount++;

                } catch (Exception e) {
                    failCount++;
                    String msg = "Lỗi tại dòng " + row.getRowNumber() + ": " + e.getMessage();
                    log.error(msg);
                    errors.add(msg);
                }
            }

            // Cập nhật trạng thái phiếu nhập
            importProduct.setStatus(failCount == 0 ? "Completed" : "Completed_With_Errors");
            importProductRepository.save(importProduct);

            String message = failCount == 0
                    ? "Nhập hàng thành công toàn bộ."
                    : "Nhập hàng hoàn tất, có " + failCount + " dòng lỗi.";

            return new ImportResultResponse(true, rows.size(), successCount, failCount, message, errors);

        } catch (Exception e) {
            log.error("Lỗi nghiêm trọng trong quá trình nhập hàng", e);
            throw e; // rollback toàn bộ
        }
    }
}
