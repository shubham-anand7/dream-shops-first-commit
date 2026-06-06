package com.shopshere.shopshere.repository;

import com.shopshere.shopshere.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 🔍 Get all reviews for a product
    List<Review> findByProductId(Long productId);

    // 🔍 Get all reviews by a user (optional but useful)
    List<Review> findByUserId(Long userId);
}