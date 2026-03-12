package com.pgplatform.guest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "room_assignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long guestId;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long hostelId;

    @Column(nullable = false)
    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Column(nullable = false)
    private Double monthlyRent;

    @Column(nullable = false)
    private Boolean active = true;
}
