package com.dailycodework.dreamshops.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Wishlist {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;



    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;



    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;



    public Wishlist(
            User user,
            Product product
    ){

        this.user = user;

        this.product = product;

    }

}