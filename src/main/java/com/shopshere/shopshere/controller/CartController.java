package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.dto.CartRequest;
import com.shopshere.shopshere.entity.Cart;
import com.shopshere.shopshere.entity.User;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // 🛒 GET CART
    @GetMapping
    public Cart getCart(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return cartService.getCartByUser(user.getId());
    }

    // ➕ ADD TO CART
    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartRequest request,
                          Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return cartService.addToCart(
                user.getId(),
                request.getProductId(),
                request.getQuantity()
        );
    }

    // 🔄 UPDATE QUANTITY
    @PutMapping("/update")
    public Cart updateQuantity(@RequestBody CartRequest request,
                               Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return cartService.updateQuantity(
                user.getId(),
                request.getProductId(),
                request.getQuantity()
        );
    }

    // ❌ REMOVE ITEM
    @DeleteMapping("/{productId}")
    public void remove(@PathVariable Long productId,
                       Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        cartService.removeFromCart(user.getId(), productId);
    }

    // 🧹 CLEAR CART
    @DeleteMapping("/clear")
    public void clear(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        cartService.clearCart(user.getId());
    }
}