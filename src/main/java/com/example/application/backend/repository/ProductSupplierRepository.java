package com.example.application.backend.repository;

import com.example.application.backend.data.entity.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
    @Override
    List<ProductSupplier> findAll();

    @Override
    <S extends ProductSupplier> S save(S entity);

    @Override
    <S extends ProductSupplier> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(ProductSupplier entity);


    Optional<ProductSupplier> findCategoryByName(String supplier);
}
