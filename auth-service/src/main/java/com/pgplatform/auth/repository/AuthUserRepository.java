package com.pgplatform.auth.repository;

import com.pgplatform.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByPhoneNumber(String phoneNumber);

    Optional<AuthUser> findByPhoneNumberAndTenantId(String phoneNumber, Long tenantId);

    //boolean existsByUsername(String username);

    boolean existsByPhoneNumberAndTenantId(String phoneNumber, Long tenantId);
}
