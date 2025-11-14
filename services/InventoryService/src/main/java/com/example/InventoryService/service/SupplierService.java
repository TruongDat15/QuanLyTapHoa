package com.example.InventoryService.service;

import com.example.InventoryService.dto.SupplierDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {

    List<SupplierDTO> getAllSuppliers();
    SupplierDTO getSupplierById(Integer id);
    SupplierDTO createSupplier(SupplierDTO supplierDTO);
    SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO);
    void deleteSupplier(Integer id);
}
