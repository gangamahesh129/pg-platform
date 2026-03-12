package com.pgplatform.user.controller;

import com.pgplatform.user.dto.UserProfileDto;
import com.pgplatform.user.entity.UserProfile;
import com.pgplatform.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileDto> createOrUpdate(@RequestBody UserProfileDto dto) {
        return ResponseEntity.ok(userProfileService.createOrUpdate(dto));
    }

    @GetMapping("/auth/{authUserId}")
    public ResponseEntity<UserProfileDto> getByAuthUserId(@PathVariable Long authUserId) {
        return ResponseEntity.ok(userProfileService.getByAuthUserId(authUserId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<UserProfileDto>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(userProfileService.getByTenantId(tenantId));
    }

    @GetMapping("/tenant/{tenantId}/role/{role}")
    public ResponseEntity<List<UserProfileDto>> getByTenantAndRole(
            @PathVariable Long tenantId, @PathVariable UserProfile.Role role) {
        return ResponseEntity.ok(userProfileService.getByTenantIdAndRole(tenantId, role));
    }
}
