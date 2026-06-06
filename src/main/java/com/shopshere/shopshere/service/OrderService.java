package com.shopshere.shopshere.service;

import com.shopshere.shopshere.entity.Order;
import com.shopshere.shopshere.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    List<Order> getUserOrders(Long userId);

    Order placeOrder(Long userId, Long addressId);

    Order updateStatus(Long orderId, OrderStatus status);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders(); // admin
}