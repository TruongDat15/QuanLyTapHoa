package com.example.InventoryService.controller;


import com.example.InventoryService.dto.request.ImportProductRequest;
import com.example.InventoryService.dto.response.ImportProductResponse;
import com.example.InventoryService.service.ImportProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory/import-product")
public class ImportProductController {

    private final ImportProductService importProductService;

    @GetMapping("/test")
    public String testImportProduct() {

        String now = java.time.LocalDateTime.now().toString();
        return "Import Product Endpoint is working!" + now;
    }


    // Tải file mẫu excel để nhập phiếu nhập hàng
    @GetMapping("/download-template")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/import_template.xlsx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=import_template.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @PostMapping
    public ResponseEntity<ImportProductResponse> create(@RequestBody ImportProductRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(importProductService.createImportProduct(request, username));
    }

    @GetMapping
    public ResponseEntity<List<ImportProductResponse>> getAllImportProducts() {
        List<ImportProductResponse> responses = importProductService.getAllImportProducts();
        return ResponseEntity.ok(responses);
    }


//    // Nhập phiếu nhập hàng từ file excel
//    @PostMapping("/upload")
//    public ResponseEntity<ImportResultResponse> uploadExcelFile(
//            @RequestParam("supplierId") Integer supplierId,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam(required = false, defaultValue = "false") boolean finalSave) throws IOException {
//        // Implementation for file upload and processing goes here
//
//        ImportResultResponse importResultResponse = importProductService.processExcelRow(file, supplierId, finalSave);
//        return ResponseEntity.ok(importResultResponse);
//    }

}
