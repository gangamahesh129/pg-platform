package com.pgplatform.auth.dto;

import com.pgplatform.auth.entity.AuthUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String fname;

    @NotBlank(message = "Last name is required")
    private String lname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Email format is invalid"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;

    private String profilePictureUrl;

    @NotNull(message = "Role is required")
    private AuthUser.Role role;

    // @NotNull(message = "Active is required")
    private Boolean active = true;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;
}
