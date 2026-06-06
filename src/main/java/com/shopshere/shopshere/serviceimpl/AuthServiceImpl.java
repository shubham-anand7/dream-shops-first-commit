package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.dto.AuthResponse;
import com.shopshere.shopshere.dto.LoginRequest;
import com.shopshere.shopshere.dto.RegisterRequest;
import com.shopshere.shopshere.entity.User;
import com.shopshere.shopshere.entity.enums.Role;
import com.shopshere.shopshere.repository.UserRepository;
import com.shopshere.shopshere.security.JwtUtil;
import com.shopshere.shopshere.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request){

        // ✅ VALIDATION
        if(request.getEmail() == null || request.getPassword() == null){
            throw new RuntimeException("Email and password are required");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        // ✅ DEFAULT ROLE
        user.setRole(Role.USER);

        userRepository.save(user);

        // ✅ TOKEN
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token,"Registered Successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request){

        if(request.getEmail() == null || request.getPassword() == null){
            throw new RuntimeException("Email and password required");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        // ❌ REMOVE THIS LINE (CRITICAL FIX)
        // user.setRole(Role.USER);

        // ✅ USE EXISTING ROLE
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token,"Login Success");
    }

    public AuthResponse registerAdmin(RegisterRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token,"Admin Registered");
    }
}