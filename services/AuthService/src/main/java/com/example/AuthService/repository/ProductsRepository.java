package com.example.AuthService.repository;

import com.example.AuthService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
