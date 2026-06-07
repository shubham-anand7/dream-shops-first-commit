package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.request.WishlistRequest;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.wishlist.IWishlistService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/wishlist")
public class WishlistController {


    private final IWishlistService wishlistService;




    @PostMapping("/add")
    public ResponseEntity<ApiResponse>
    addWishlist(

            @RequestBody WishlistRequest request

    ){


        return ResponseEntity.ok(

                new ApiResponse(

                        "Added to wishlist ❤️",

                        wishlistService.addWishlist(
                                request
                        )

                )

        );

    }





    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse>
    getWishlist(

            @PathVariable Long userId

    ){


        return ResponseEntity.ok(

                new ApiResponse(

                        "Wishlist",

                        wishlistService.getWishlist(
                                userId
                        )

                )

        );

    }







    @DeleteMapping(
            "/remove/{userId}/{productId}"
    )
    public ResponseEntity<ApiResponse>
    remove(

            @PathVariable Long userId,

            @PathVariable Long productId

    ){


        wishlistService.removeWishlist(
                userId,
                productId
        );



        return ResponseEntity.ok(

                new ApiResponse(

                        "Removed ❤️",

                        null

                )

        );

    }



}