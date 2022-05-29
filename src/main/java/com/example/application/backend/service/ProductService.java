package com.example.application.backend.service;

import com.example.application.backend.data.entity.Product;
import com.example.application.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService<Product> {

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

}
