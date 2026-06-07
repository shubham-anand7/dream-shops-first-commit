package com.dailycodework.dreamshops.service.wishlist;


import com.dailycodework.dreamshops.model.Wishlist;
import com.dailycodework.dreamshops.request.WishlistRequest;


import java.util.List;


public interface IWishlistService {


    Wishlist addWishlist(
            WishlistRequest request
    );


    List<Wishlist> getWishlist(
            Long userId
    );



    void removeWishlist(
            Long userId,
            Long productId
    );


}