package com.pgplatform.user.dto;

import com.pgplatform.user.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {

    private Long id;
    private Long authUserId;
    private Long tenantId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private UserProfile.Role role;
}
