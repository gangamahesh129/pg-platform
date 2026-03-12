package com.pgplatform.auth.repository;

import com.pgplatform.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUsername(String username);

    Optional<AuthUser> findByUsernameAndTenantId(String username, Long tenantId);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndTenantId(String username, Long tenantId);
}
