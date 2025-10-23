
package com.example.demo.repository;

import com.example.demo.entity.Supplier;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


//@RepositoryRestResource(collectionResourceRel = "supplier", path = "supplier")
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}

