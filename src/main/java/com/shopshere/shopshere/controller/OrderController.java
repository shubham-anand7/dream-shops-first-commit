package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.entity.Order;
import com.shopshere.shopshere.entity.User;
import com.shopshere.shopshere.entity.enums.OrderStatus;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    // 📦 PLACE ORDER
    @PostMapping
    public Order placeOrder(@RequestParam Long addressId,
                            Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return orderService.placeOrder(user.getId(), addressId);
    }

    // 📜 USER ORDERS
    @GetMapping
    public List<Order> getUserOrders(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return orderService.getUserOrders(user.getId());
    }

    // 🔍 GET SINGLE ORDER
    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // 👑 ADMIN - ALL ORDERS
    @GetMapping("/admin")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 🚚 STATUS UPDATE
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam OrderStatus status) {

        return orderService.updateStatus(id, status);
    }
}