package com.dailycodework.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    private int rating;


    private String comment;


    private LocalDate createdDate;


    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;


    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;


    public Review(
            int rating,
            String comment,
            Product product,
            User user
    ){

        this.rating = rating;
        this.comment = comment;
        this.product = product;
        this.user = user;
        this.createdDate =
                LocalDate.now();

    }

}