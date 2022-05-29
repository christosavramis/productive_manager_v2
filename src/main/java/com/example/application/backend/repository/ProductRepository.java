package com.example.application.backend.repository;

import com.example.application.backend.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    <S extends Product> S save(S entity);

    @Override
    void delete(Product entity);
}
