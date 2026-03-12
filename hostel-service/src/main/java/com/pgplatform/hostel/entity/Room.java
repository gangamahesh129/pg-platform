package com.pgplatform.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostelId;

    private Long floorId;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity = 1;

    @Column(nullable = false)
    private Double monthlyRent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;

    public enum RoomStatus {
        AVAILABLE, OCCUPIED, MAINTENANCE
    }
}
