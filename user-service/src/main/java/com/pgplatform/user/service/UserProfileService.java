package com.pgplatform.user.service;

import com.pgplatform.user.dto.UserProfileDto;
import com.pgplatform.user.entity.UserProfile;
import com.pgplatform.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileDto createOrUpdate(UserProfileDto dto) {
        UserProfile profile = UserProfile.builder()
                .id(dto.getId())
                .authUserId(dto.getAuthUserId())
                .tenantId(dto.getTenantId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .profilePicture(dto.getProfilePicture())
                .role(dto.getRole())
                .build();
        profile = userProfileRepository.save(profile);
        return toDto(profile);
    }

    public UserProfileDto getByAuthUserId(Long authUserId) {
        return userProfileRepository.findByAuthUserId(authUserId)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User profile not found: " + authUserId));
    }

    public UserProfileDto getByAuthUserIdAndTenant(Long authUserId, Long tenantId) {
        return userProfileRepository.findByAuthUserIdAndTenantId(authUserId, tenantId)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
    }

    public List<UserProfileDto> getByTenantId(Long tenantId) {
        return userProfileRepository.findByTenantId(tenantId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserProfileDto> getByTenantIdAndRole(Long tenantId, UserProfile.Role role) {
        return userProfileRepository.findByTenantIdAndRole(tenantId, role).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UserProfileDto toDto(UserProfile profile) {
        return UserProfileDto.builder()
                .id(profile.getId())
                .authUserId(profile.getAuthUserId())
                .tenantId(profile.getTenantId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .phoneNumber(profile.getPhoneNumber())
                .profilePicture(profile.getProfilePicture())
                .role(profile.getRole())
                .build();
    }
}
