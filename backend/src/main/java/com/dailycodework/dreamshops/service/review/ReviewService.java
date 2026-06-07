package com.dailycodework.dreamshops.service.review;


import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.model.Review;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.repository.ReviewRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.request.ReviewRequest;
import com.dailycodework.dreamshops.response.RatingResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class ReviewService
        implements IReviewService {


    private final ReviewRepository reviewRepository;


    private final ProductRepository productRepository;


    private final UserRepository userRepository;




    @Override
    public Review addReview(
            ReviewRequest request
    ) {


        Product product =
                productRepository
                        .findById(
                                request.getProductId()
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Product not found"
                                        )
                        );



        User user =
                userRepository
                        .findById(
                                request.getUserId()
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found"
                                        )
                        );



        Review review =
                new Review();


        review.setProduct(product);

        review.setUser(user);

        review.setRating(
                request.getRating()
        );

        review.setComment(
                request.getComment()
        );



        return reviewRepository
                .save(review);

    }






    @Override
    public List<Review> getReviews(
            Long productId
    ) {


        return reviewRepository
                .findByProductId(
                        productId
                );

    }






    @Override
    public RatingResponse getProductRating(
            Long productId
    ) {



        List<Review> reviews =
                reviewRepository
                        .findByProductId(
                                productId
                        );



        double average =
                reviews.stream()

                        .mapToInt(
                                Review::getRating
                        )

                        .average()

                        .orElse(0);




        Long count =
                reviewRepository
                        .countByProductId(
                                productId
                        );




        return new RatingResponse(

                average,

                count

        );

    }



}