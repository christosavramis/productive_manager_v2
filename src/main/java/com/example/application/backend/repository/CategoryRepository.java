package com.example.application.backend.repository;

import com.example.application.backend.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();

    @Override
    <S extends Category> S save(S entity);

    @Override
    <S extends Category> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(Category entity);


    Optional<Category> findCategoryByName(String category);
}
