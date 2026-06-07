package com.dailycodework.dreamshops.service.review;


import com.dailycodework.dreamshops.model.Review;
import com.dailycodework.dreamshops.request.ReviewRequest;
import com.dailycodework.dreamshops.response.RatingResponse;

import java.util.List;


public interface IReviewService {


    Review addReview(
            ReviewRequest request
    );


    List<Review> getReviews(
            Long productId
    );


    RatingResponse getProductRating(
            Long productId
    );


}