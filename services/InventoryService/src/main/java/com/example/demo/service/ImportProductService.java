package com.example.demo.service;

import com.example.demo.dto.response.ImportResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public interface ImportProductService {
    ImportResultResponse processExcelRow(MultipartFile file, Integer supplierId, Boolean finalSave) throws IOException;
}
