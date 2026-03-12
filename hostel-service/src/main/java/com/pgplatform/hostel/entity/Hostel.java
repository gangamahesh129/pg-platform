package com.pgplatform.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hostel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long ownerId; // auth_user_id of owner

    @Column(nullable = false)
    private String name;

    private String street;
    private String city;
    private String state;
    private Integer pinCode;

    private String contactPhone;
    private String contactEmail;
}
