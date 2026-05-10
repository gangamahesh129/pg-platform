package com.pgplatform.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String phonenumber;

    @NotBlank(message = "Password is required")
    private String password;

    private Long tenantId; // Optional - for multi-tenant login
}
