package com.pgplatform.user.repository;

import com.pgplatform.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByAuthUserId(Long authUserId);

    Optional<UserProfile> findByAuthUserIdAndTenantId(Long authUserId, Long tenantId);

    List<UserProfile> findByTenantId(Long tenantId);

    List<UserProfile> findByTenantIdAndRole(Long tenantId, UserProfile.Role role);
}
