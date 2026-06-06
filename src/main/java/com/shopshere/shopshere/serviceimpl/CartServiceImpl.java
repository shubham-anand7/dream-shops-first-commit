package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.entity.Cart;
import com.shopshere.shopshere.entity.CartItem;
import com.shopshere.shopshere.entity.Product;
import com.shopshere.shopshere.repository.CartRepository;
import com.shopshere.shopshere.repository.ProductRepository;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public Cart addToCart(Long userId, Long productId, int quantity) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found")));
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(Long userId, Long productId, int quantity) {

        Cart cart = getCartByUser(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            if (item.getProduct().getStock() < quantity) {
                throw new RuntimeException("Not enough stock");
            }
            item.setQuantity(quantity);
        }

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {

        Cart cart = getCartByUser(userId);

        cart.getItems().removeIf(item ->
                item.getProduct().getId().equals(productId)
        );

        updateCartTotal(cart);
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {

        Cart cart = getCartByUser(userId);

        cart.getItems().clear();
        cart.setTotalPrice(0.0);

        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {

        double total = cart.getItems().stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity())
                .sum();

        cart.setTotalPrice(total);
    }
}