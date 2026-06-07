package com.dailycodework.dreamshops.service.dashboard;

import com.dailycodework.dreamshops.repository.OrderRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.response.DashboardResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardService
        implements IDashboardService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    @Override
    public DashboardResponse getDashboard() {

        Long totalProducts =
                productRepository.count();

        Long totalUsers =
                userRepository.count();

        Long totalOrders =
                orderRepository.count();

        BigDecimal revenue =
                orderRepository.findAll()
                        .stream()
                        .map(order ->
                                order.getTotalAmount()
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        return new DashboardResponse(
                totalProducts,
                totalUsers,
                totalOrders,
                revenue
        );
    }
}