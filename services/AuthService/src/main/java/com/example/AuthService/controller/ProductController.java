package com.example.AuthService.controller;

import com.example.AuthService.entity.Product;
import com.example.AuthService.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRepository productsRepository;

//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }
    
    @GetMapping("/hello")
    public String helloAdmin() {
        Authentication authentication =  org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        log.info("Authenticated user: {}", authentication.getName());
        authentication.getAuthorities().forEach(authority ->
                log.info("Authority: {}", authority.getAuthority())
        );
        log.info("Accessing admin hello endpoint");
        return "Hello, Admin!";
    }

}
