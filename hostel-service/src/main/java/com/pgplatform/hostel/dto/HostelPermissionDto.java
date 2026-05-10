package com.pgplatform.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelPermissionDto {
    private Long id;
    private String teantId;
    private Long ownerId;   // auth_user_id of owner
    private String role = HostelDto.HostelRole.OE_PUBLIC.name();
}
