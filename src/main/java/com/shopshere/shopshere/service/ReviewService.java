package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Long userId, Long productId, int rating, String comment);
    List<Review> getProductReviews(Long productId);
}