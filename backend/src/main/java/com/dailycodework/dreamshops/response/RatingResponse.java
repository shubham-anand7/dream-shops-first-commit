package com.dailycodework.dreamshops.response;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RatingResponse {


    private double averageRating;


    private Long totalReviews;


}