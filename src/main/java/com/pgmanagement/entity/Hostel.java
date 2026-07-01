package com.pgmanagement.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    private String street;
    private String city;
    private String state;
    private Integer pinCode;

    private String contactPhone;
    private String contactEmail;
}
