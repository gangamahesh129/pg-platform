package com.pgplatform.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long hostelId;

    @Column(nullable = false)
    private Long guestId;

    @Column(nullable = false)
    private Long assignmentId;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double totalAmount;

    private Double lateFee;
    private Double partialPayment;
    private Double dueAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    private LocalDate dueDate;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum InvoiceStatus {
        PENDING, PAID, PARTIAL, OVERDUE
    }
}
