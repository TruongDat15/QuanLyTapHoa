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
