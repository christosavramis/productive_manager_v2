package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    @Override
    List<Audit> findAll();

    @Override
    <S extends Audit> S save(S entity);

    @Override
    <S extends Audit> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Audit entity);

}
