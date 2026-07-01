package com.pgmanagement.service;

import com.pgmanagement.dto.LoginRequest;
import com.pgmanagement.dto.LoginResponse;
import com.pgmanagement.dto.RegisterRequest;
import com.pgmanagement.entity.User;
import com.pgmanagement.repository.UserRepository;
import com.pgmanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (request.getTenantId() != null && !user.getTenantId().equals(request.getTenantId())) {
            throw new RuntimeException("Invalid tenant");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtUtil.generateToken(
                user.getPhoneNumber(),
                user.getRole().name(),
                user.getTenantId(),
                user.getId()
        );

        return LoginResponse.builder()
                .token(token)
                .username(user.getPhoneNumber())
                .role(user.getRole().name())
                .tenantId(user.getTenantId())
                .userId(user.getId())
                .expiresIn(3600000L)
                .build();
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsernameAndTenantId(request.getPassword(), request.getTenantId())) {
            throw new RuntimeException("Username already exists for this tenant");
        }

        User user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .tenantId(request.getTenantId())
                .active(true)
                .build();

        return userRepository.save(user);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
