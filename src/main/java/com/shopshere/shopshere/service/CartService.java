package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Cart;

public interface CartService {
    Cart getCartByUser(Long userId);
    Cart addToCart(Long userId, Long productId, int quantity);
    Cart updateQuantity(Long userId, Long productId, int quantity);
    void removeFromCart(Long userId, Long productId);
    void clearCart(Long userId);
}