package com.pgplatform.hostel.dto;

import com.pgplatform.hostel.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

    private Long id;
    private Long hostelId;
    private Long floorId;
    private String roomNumber;
    private Integer capacity;
    private Double monthlyRent;
    private Room.RoomStatus status;
}
