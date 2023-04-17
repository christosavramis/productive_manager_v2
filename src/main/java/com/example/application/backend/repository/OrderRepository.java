package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    List<Order> findAll();

    @Override
    <S extends Order> S save(S entity);

    @Override
    <S extends Order> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Order entity);

}
