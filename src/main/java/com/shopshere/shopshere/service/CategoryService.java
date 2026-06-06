package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(String name);

    List<Category> getAll();
}