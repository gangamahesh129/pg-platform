package com.pgplatform.hostel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hosteladdress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostelAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private Integer pinCode;

    private String contactPhone;
    private String contactEmail;
}
