package com.pgplatform.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelDto {
    private Long id;
    private String tenantId;
    private String hostelName;
    private HostelAddressDto hostelAddress;
    private List<HostelPermissionDto> permissions;

    public  enum HostelRole {
        OE_ADMIN, OE_PUBLIC
    }
}
