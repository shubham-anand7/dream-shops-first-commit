package com.shopshere.shopshere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {

    private Long userId;
    private Long productId;
    private int quantity;
}