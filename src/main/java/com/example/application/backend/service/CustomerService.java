package com.example.application.backend.service;

import com.example.application.backend.data.entity.Customer;
import com.example.application.backend.data.entity.Order;
import com.example.application.backend.repository.CustomerRepository;
import com.example.application.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {

    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

}
