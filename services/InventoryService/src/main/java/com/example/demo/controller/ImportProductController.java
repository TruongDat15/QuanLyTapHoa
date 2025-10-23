package com.example.demo.controller;


import com.example.demo.dto.response.ImportResultResponse;
import com.example.demo.helper.ExcelTemplateHelper;
import com.example.demo.service.ImportProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory/import-product")
public class ImportProductController {

    private final ImportProductService importProductService;

    @GetMapping("/test")
    public String testImportProduct() {
        return "Import Product Endpoint is working!";
    }

    @GetMapping("/download-template")
    public ResponseEntity<InputStreamResource> downloadImportTemplate() throws IOException {
        ByteArrayInputStream in = ExcelTemplateHelper.createImportTemplate();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=import_template.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImportResultResponse> uploadExcelFile(
            @RequestParam("supplierId") Integer supplierId,
            @RequestParam("file") MultipartFile file) throws IOException {
        // Implementation for file upload and processing goes here

        ImportResultResponse importResultResponse = importProductService.processExcelRow(file, supplierId);
        return ResponseEntity.ok(importResultResponse);
    }

}
