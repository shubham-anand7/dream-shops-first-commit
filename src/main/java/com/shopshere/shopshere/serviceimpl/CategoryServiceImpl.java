package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.entity.Category;
import com.shopshere.shopshere.repository.CategoryRepository;
import com.shopshere.shopshere.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(String name) {

        // prevent duplicate category
        categoryRepository.findByName(name).ifPresent(c -> {
            throw new RuntimeException("Category already exists");
        });

        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}