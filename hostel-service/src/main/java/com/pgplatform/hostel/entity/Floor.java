package com.pgplatform.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "floor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostelId;

    @Column(nullable = false)
    private Integer floorNumber;

    private String name;
}
