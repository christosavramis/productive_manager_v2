package com.example.application.backend.service;

import com.example.application.backend.data.entity.Product;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService<Product> {

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public void delete(Product object) {
        object.setMarkedForDelete(true);
        super.saveNoAudit(object);
    }

    @Override
    public List<Product> findAll() {
        return super.findAll().stream().filter(product -> !product.isMarkedForDelete()).collect(Collectors.toList());
    }
}
