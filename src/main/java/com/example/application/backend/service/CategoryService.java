package com.example.application.backend.service;

import com.example.application.backend.data.entity.Category;
import com.example.application.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category> {

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

}
