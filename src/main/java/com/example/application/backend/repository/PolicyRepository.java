package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Override
    List<Policy> findAll();

    @Override
    <S extends Policy> S save(S entity);

    @Override
    <S extends Policy> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Policy entity);

}
