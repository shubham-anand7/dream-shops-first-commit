package com.dailycodework.dreamshops.service.wishlist;


import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.model.Wishlist;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.repository.WishlistRepository;
import com.dailycodework.dreamshops.request.WishlistRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class WishlistService
        implements IWishlistService {



    private final WishlistRepository wishlistRepository;


    private final ProductRepository productRepository;


    private final UserRepository userRepository;




    @Override
    public Wishlist addWishlist(
            WishlistRequest request
    ){


        Product product =
                productRepository.findById(
                                request.getProductId()
                        )
                        .orElseThrow(
                                ()-> new ResourceNotFoundException(
                                        "Product not found"
                                )
                        );



        User user =
                userRepository.findById(
                                request.getUserId()
                        )
                        .orElseThrow(
                                ()-> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );




        return wishlistRepository.save(

                new Wishlist(
                        user,
                        product
                )

        );


    }






    @Override
    public List<Wishlist> getWishlist(
            Long userId
    ){

        return wishlistRepository
                .findByUserId(userId);

    }





    @Override
    public void removeWishlist(
            Long userId,
            Long productId
    ){

        wishlistRepository
                .deleteByUserIdAndProductId(
                        userId,
                        productId
                );

    }



}