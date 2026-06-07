package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.dashboard.IDashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/dashboard")
public class DashboardController {

    private final IDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse> getDashboard() {

        return ResponseEntity.ok(
                new ApiResponse(
                        "Dashboard",
                        dashboardService.getDashboard()
                )
        );
    }
}