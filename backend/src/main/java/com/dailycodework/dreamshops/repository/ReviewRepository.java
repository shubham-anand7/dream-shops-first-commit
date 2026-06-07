package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {


    List<Review> findByProductId(
            Long productId
    );


    Long countByProductId(
            Long productId
    );

}