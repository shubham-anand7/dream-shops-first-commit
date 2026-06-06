package com.shopshere.shopshere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
}