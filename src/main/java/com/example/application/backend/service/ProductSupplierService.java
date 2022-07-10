package com.example.application.backend.service;

import com.example.application.backend.data.entity.ProductSupplier;
import com.example.application.backend.repository.ProductSupplierRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductSupplierService extends AbstractService<ProductSupplier> {

    public ProductSupplierService(ProductSupplierRepository repository) {
        super(repository);
    }

}
