package com.shopshere.shopshere.service;

import com.shopshere.shopshere.dto.ProductRequestDTO;
import com.shopshere.shopshere.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<Product> getAll(Pageable pageable);

    Page<Product> search(String keyword, Pageable pageable);

    Page<Product> filter(Long categoryId, String keyword, Pageable pageable);

    Product create(ProductRequestDTO dto);

    Product update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    Product getById(Long id);
}