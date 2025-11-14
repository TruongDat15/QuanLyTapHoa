
package com.example.InventoryService.repository;

import com.example.InventoryService.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}

