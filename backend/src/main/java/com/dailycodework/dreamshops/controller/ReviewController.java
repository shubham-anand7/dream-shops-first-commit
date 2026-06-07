package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.model.Review;
import com.dailycodework.dreamshops.request.ReviewRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.response.RatingResponse;
import com.dailycodework.dreamshops.service.review.IReviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reviews")
public class ReviewController {


    private final IReviewService reviewService;



    // ADD REVIEW
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addReview(

            @RequestBody ReviewRequest request

    ) {


        Review review =
                reviewService
                        .addReview(request);



        return ResponseEntity.ok(

                new ApiResponse(

                        "Review Added Successfully",

                        review
                )

        );

    }






    // GET PRODUCT REVIEWS
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getReviews(

            @PathVariable Long productId

    ) {


        List<Review> reviews =
                reviewService
                        .getReviews(
                                productId
                        );



        return ResponseEntity.ok(

                new ApiResponse(

                        "Reviews",

                        reviews
                )

        );


    }








    // GET AVERAGE RATING
    @GetMapping("/rating/{productId}")
    public ResponseEntity<ApiResponse> getRating(

            @PathVariable Long productId

    ) {



        RatingResponse rating =

                reviewService
                        .getProductRating(
                                productId
                        );




        return ResponseEntity.ok(

                new ApiResponse(

                        "Rating",

                        rating

                )

        );


    }




}