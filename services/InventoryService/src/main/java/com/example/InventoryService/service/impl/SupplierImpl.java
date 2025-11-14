package com.example.InventoryService.service.impl;

import com.example.InventoryService.dto.SupplierDTO;
import com.example.InventoryService.entity.Supplier;
import com.example.InventoryService.repository.SupplierRepository;
import com.example.InventoryService.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        return toDTO(supplier);
    }

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = Supplier.builder()
                .supplierName(supplierDTO.getSupplierName())
                .phone(supplierDTO.getPhone())
                .email(supplierDTO.getEmail())
                .address(supplierDTO.getAddress())
                .note(supplierDTO.getNote())
                .build();
        Supplier saved = supplierRepository.save(supplier);
        return toDTO(saved);
    }

    @Override
    public SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

        existing.setSupplierName(supplierDTO.getSupplierName());
        existing.setPhone(supplierDTO.getPhone());
        existing.setEmail(supplierDTO.getEmail());
        existing.setAddress(supplierDTO.getAddress());
        existing.setNote(supplierDTO.getNote());

        Supplier updated = supplierRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteSupplier(Integer id) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        supplierRepository.delete(existing);
    }

    private SupplierDTO toDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .supplierId(supplier.getSupplierId())
                .supplierName(supplier.getSupplierName())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .note(supplier.getNote())
                .build();
    }
}
