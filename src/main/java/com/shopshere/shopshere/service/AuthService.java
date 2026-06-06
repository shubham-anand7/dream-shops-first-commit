package com.shopshere.shopshere.service;

import com.shopshere.shopshere.dto.AuthResponse;
import com.shopshere.shopshere.dto.LoginRequest;
import com.shopshere.shopshere.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}