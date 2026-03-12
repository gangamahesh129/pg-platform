package com.pgplatform.billing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long invoiceId;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    private String transactionId;
    private String notes;

    @Column(nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    public enum PaymentMethod {
        CASH, UPI, CARD, BANK_TRANSFER, OTHER
    }
}
