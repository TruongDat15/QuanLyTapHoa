package com.example.CustomerService.repository;


import com.example.CustomerService.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByPhone(String phone);
}
