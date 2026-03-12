package com.pgplatform.auth.controller;

import com.pgplatform.auth.dto.LoginRequest;
import com.pgplatform.auth.dto.LoginResponse;
import com.pgplatform.auth.dto.RegisterRequest;
import com.pgplatform.auth.entity.AuthUser;
import com.pgplatform.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthUser> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") 
                ? authHeader.substring(7) 
                : null;
        boolean valid = token != null && authService.validateToken(token);
        return ResponseEntity.ok(Map.of("valid", valid));
    }
}
