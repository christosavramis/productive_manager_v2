package com.example.application.backend.service;

import com.example.application.backend.data.entities.Product;
import com.example.application.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

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

    public List<Product> findAllEnabled() {
        return super.findAll().stream().filter(Product::isEnabled).filter(not(Product::isMarkedForDelete)).collect(Collectors.toList());
    }
}
