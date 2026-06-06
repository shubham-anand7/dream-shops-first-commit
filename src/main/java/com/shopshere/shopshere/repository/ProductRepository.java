package com.shopshere.shopshere.repository;

import com.shopshere.shopshere.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 🔍 Search
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // 🔍 Filter by category
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // 🔍 Combined filter
    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(
            Long categoryId,
            String keyword,
            Pageable pageable
    );
}