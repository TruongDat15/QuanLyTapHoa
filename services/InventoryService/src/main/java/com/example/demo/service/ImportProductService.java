package com.example.demo.service;

import com.example.demo.dto.request.ImportProductRequest;
import com.example.demo.dto.response.ImportProductResponse;
import com.example.demo.dto.response.ImportResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

