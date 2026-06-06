package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.entity.Review;
import com.shopshere.shopshere.entity.User;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @PostMapping("/{productId}")
    public Review addReview(@PathVariable Long productId,
                            @RequestBody Map<String, Object> body,
                            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return reviewService.addReview(
                user.getId(),
                productId,
                (Integer) body.get("rating"),
                (String) body.get("comment")
        );
    }

    @GetMapping("/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {
        return reviewService.getProductReviews(productId);
    }
}