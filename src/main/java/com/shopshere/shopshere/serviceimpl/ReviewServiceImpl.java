package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.entity.Product;
import com.shopshere.shopshere.entity.Review;
import com.shopshere.shopshere.entity.User;
import com.shopshere.shopshere.repository.ProductRepository;
import com.shopshere.shopshere.repository.ReviewRepository;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Review addReview(Long userId, Long productId, int rating, String comment) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}