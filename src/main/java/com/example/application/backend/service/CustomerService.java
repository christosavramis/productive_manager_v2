package com.example.application.backend.service;

import com.example.application.backend.data.entities.Customer;
import com.example.application.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {

    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

}
