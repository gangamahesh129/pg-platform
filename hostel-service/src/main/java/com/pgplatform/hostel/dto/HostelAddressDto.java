package com.pgplatform.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelAddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private Integer pinCode;

    private String contactPhone;
    private String contactEmail;
}
