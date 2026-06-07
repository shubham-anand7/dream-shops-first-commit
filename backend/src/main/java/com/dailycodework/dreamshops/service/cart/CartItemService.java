package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;

    @Override
    public Cart addItemToCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);

        Product product = productService.getProductById(productId);

        // ✅ FIND BY PRODUCT ID
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct().getId().equals(productId)
                )
                .findFirst()
                .orElse(new CartItem());

        // ✅ NEW ITEM
        if (cartItem.getId() == null) {

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());

        } else {

            // ✅ UPDATE EXISTING QTY
            cartItem.setQuantity(
                    cartItem.getQuantity() + quantity
            );
        }

        cartItem.setTotalPrice();

        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);

        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {

        Cart cart = cartService.getCart(cartId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found")
                );

        // ✅ SECURITY CHECK
        if (!item.getCart().getId().equals(cartId)) {
            throw new ResourceNotFoundException(
                    "Item does not belong to cart"
            );
        }

        cart.getItems().remove(item);

        cartItemRepository.delete(item);

        updateCartTotal(cart);

        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(
            Long cartId,
            Long itemId,
            int quantity
    ) {

        Cart cart = cartService.getCart(cartId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found")
                );

        // ✅ REMOVE IF QTY <= 0
        if (quantity <= 0) {
            removeItemFromCart(cartId, itemId);
            return;
        }

        item.setQuantity(quantity);

        item.setUnitPrice(item.getProduct().getPrice());

        item.setTotalPrice();

        cartItemRepository.save(item);

        updateCartTotal(cart);

        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long itemId) {

        Cart cart = cartService.getCart(cartId);

        return cart.getItems()
                .stream()
                .filter(item ->
                        item.getId().equals(itemId)
                )
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Item not found")
                );
    }

    // ✅ RECALCULATE TOTAL
    private void updateCartTotal(Cart cart) {

        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
    }
}