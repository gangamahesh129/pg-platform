package com.pgmanagement.repository;

import com.pgmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :username AND u.tenantId = :tenantId")
    Optional<User> findByUsernameAndTenantId(@Param("username") String username, @Param("tenantId") Long tenantId);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.phoneNumber = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.phoneNumber = :username AND u.tenantId = :tenantId")
    boolean existsByUsernameAndTenantId(@Param("username") String username, @Param("tenantId") Long tenantId);
}
