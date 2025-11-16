package com.example.CustomerService.service;




import com.example.CustomerService.entity.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer save(Customer customer) {
        // không cho phép tạo khách hàng trùng
        Optional<Customer> optionalCustomer = repository.findByPhone(customer.getPhone());
        // if creating new (no id) and a customer with same phone exists -> conflict
        if (optionalCustomer.isPresent()) {
            Customer existing = optionalCustomer.get();
            if (customer.getId() == null || !existing.getId().equals(customer.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with this phone already exists");
            }
        }
        // ensure gender is set and valid
        if (customer.getGender() == null) {
            customer.setGender(0); // default unknown
        } else {
            int g = customer.getGender();
            if (g < 0 || g > 2) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid gender value. Allowed: 0,1,2");
            }
        }
        try {
            return repository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Optional<Customer> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Customer> getByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
