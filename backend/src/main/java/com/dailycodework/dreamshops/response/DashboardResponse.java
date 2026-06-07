package com.dailycodework.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private Long totalProducts;

    private Long totalUsers;

    private Long totalOrders;

    private BigDecimal totalRevenue;
}