package com.example.CustomerService.service;




import com.example.CustomerService.entity.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
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
