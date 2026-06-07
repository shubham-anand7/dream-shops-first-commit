package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.request.LoginRequest;
import com.dailycodework.dreamshops.request.RegisterRequest;
import com.dailycodework.dreamshops.response.LoginResponse;
import com.dailycodework.dreamshops.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dailycodework.dreamshops.service.cart.ICartService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final ICartService cartService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            @RequestParam(required = false) Long cartId
    ) {

        String token = authService.login(request);

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        // ✅ ATTACH GUEST CART TO USER
        if (cartId != null) {
            cartService.assignCartToUser(cartId, user);
        }

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        user.getId(),
                        user.getFirstName(),
                        user.getEmail()
                )
        );
    }
}