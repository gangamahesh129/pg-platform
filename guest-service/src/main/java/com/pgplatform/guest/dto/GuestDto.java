package com.pgplatform.guest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDto {

    private Long id;
    private Long hostelId;
    private Long tenantId;
    private String name;
    private String phoneNumber;
    private String email;
    private String idProof;
    private String emergencyContact;
    private String address;
    private LocalDateTime createdAt;
}
