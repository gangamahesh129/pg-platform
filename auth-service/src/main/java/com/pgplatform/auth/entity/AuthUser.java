package com.pgplatform.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fname;

    @Column(nullable = false)
    private String lname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(name = "profilepictureurl")
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Boolean active = true;

    public enum Role {
        OWNER, ADMIN, WARDEN, ACCOUNTANT
    }
}
