package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.dto.CategoryRequestDTO;
import com.shopshere.shopshere.entity.Category;
import com.shopshere.shopshere.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category create(@RequestBody CategoryRequestDTO dto) {
        return categoryService.create(dto.getName());
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }
}