package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    List<Customer> findAll();

    @Override
    <S extends Customer> S save(S entity);

    @Override
    <S extends Customer> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Customer entity);

}
