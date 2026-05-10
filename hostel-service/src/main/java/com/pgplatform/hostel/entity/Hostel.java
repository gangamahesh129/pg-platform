package com.pgplatform.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String tenantId;

    @Column(nullable = false)
    private String hostelName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private HostelAddress hostelAddress;


    @OneToMany(mappedBy = "hostel",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<HostelPermission> permissions;

}
