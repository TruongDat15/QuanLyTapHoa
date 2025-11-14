package com.example.InventoryService.service;

import com.example.InventoryService.dto.request.ImportProductRequest;
import com.example.InventoryService.dto.response.ImportProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public interface ImportProductService {
  //  ImportResultResponse processExcelRow(MultipartFile file, Integer supplierId, Boolean finalSave) throws IOException;

    ImportProductResponse createImportProduct(ImportProductRequest request, String username);



    ImportProductResponse updateImportProduct(Integer importProductId, ImportProductRequest request, boolean complete);

    List<ImportProductResponse> getAll(Integer importProductId);

    @Transactional(readOnly = true)
    List<ImportProductResponse> getAllImportProducts();

//    @Transactional(readOnly = true)
//    List<ImportProductResponse> getAllImportProducts();
}

