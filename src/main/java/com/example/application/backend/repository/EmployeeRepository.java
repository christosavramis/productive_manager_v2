package com.example.application.backend.repository;

import com.example.application.backend.data.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Override
    List<Employee> findAll();

    @Override
    <S extends Employee> S save(S entity);

    @Override
    <S extends Employee> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Employee entity);

    Optional<Employee> findEmployeeByUsername(String username);
}
