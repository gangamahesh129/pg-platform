package com.pgplatform.guest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAssignmentDto {

    private Long id;
    private Long guestId;
    private Long roomId;
    private Long hostelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double monthlyRent;
    private Boolean active;
}
