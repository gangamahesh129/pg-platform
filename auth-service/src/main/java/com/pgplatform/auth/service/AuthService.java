package com.pgplatform.auth.service;

import com.pgplatform.auth.dto.LoginRequest;
import com.pgplatform.auth.dto.LoginResponse;
import com.pgplatform.auth.dto.RegisterRequest;
import com.pgplatform.auth.entity.AuthUser;
import com.pgplatform.auth.repository.AuthUserRepository;
import com.pgplatform.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        String phonenumber = request.getPhonenumber();
        String password = request.getPassword();

        AuthUser authUser = authUserRepository.findByPhoneNumber(phonenumber)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (request.getTenantId() != null && !authUser.getTenantId().equals(request.getTenantId())) {
            throw new RuntimeException("Invalid tenant");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phonenumber, password)
        );

        String token = jwtUtil.generateToken(
                authUser.getPhoneNumber(),
                authUser.getRole().name(),
                authUser.getTenantId(),
                authUser.getId()
        );

        return LoginResponse.builder()
                .token(token)
                .username(authUser.getPhoneNumber())
                .role(authUser.getRole().name())
                .tenantId(authUser.getTenantId())
                .userId(authUser.getId())
                .expiresIn(3600000L)
                .build();
    }

    public AuthUser  register(RegisterRequest request) {
        if (authUserRepository.existsByPhoneNumberAndTenantId(request.getPassword(), request.getTenantId())) {
            throw new RuntimeException("Username already exists for this tenant");
        }

        AuthUser authUser = AuthUser.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .tenantId(request.getTenantId())
                .active(true)
                .build();

        return authUserRepository.save(authUser);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
