package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    @Override
    List<Tax> findAll();

    @Override
    <S extends Tax> S save(S entity);

    @Override
    <S extends Tax> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Tax entity);


    Optional<Tax> findTaxByName(String category);
}
