package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.entity.*;
import com.shopshere.shopshere.entity.enums.OrderStatus;
import com.shopshere.shopshere.entity.enums.PaymentStatus;
import com.shopshere.shopshere.repository.AddressRepository;
import com.shopshere.shopshere.repository.CartRepository;
import com.shopshere.shopshere.repository.OrderRepository;
import com.shopshere.shopshere.repository.ProductRepository;
import com.shopshere.shopshere.service.OrderService;
import com.shopshere.shopshere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;
    private final AddressRepository addressRepository;




    @Override
    public Order placeOrder(Long userId, Long addressId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        // ✅ ADDRESS SUPPORT


        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() == null || product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for " + product.getName());
            }

            // reduce stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(product.getPrice());

            orderItems.add(item);
        }

        order.setItems(orderItems);

        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        // 💳 PAYMENT LINK
        paymentService.createPayment(savedOrder.getId());

        // 🧹 CLEAR CART
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public Order updateStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        validateStatusTransition(order.getStatus(), status);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {

        if (current == OrderStatus.CREATED && next != OrderStatus.CONFIRMED)
            throw new RuntimeException("Invalid transition");

        if (current == OrderStatus.CONFIRMED && next != OrderStatus.SHIPPED)
            throw new RuntimeException("Invalid transition");

        if (current == OrderStatus.SHIPPED && next != OrderStatus.OUT_FOR_DELIVERY)
            throw new RuntimeException("Invalid transition");

        if (current == OrderStatus.OUT_FOR_DELIVERY && next != OrderStatus.DELIVERED)
            throw new RuntimeException("Invalid transition");
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

}