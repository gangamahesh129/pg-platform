package com.pgmanagement.dto;

import com.pgmanagement.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    private Long id;
    private Long tenantId;
    private Long hostelId;
    private Long guestId;
    private Long assignmentId;
    private Integer month;
    private Integer year;
    private Double totalAmount;
    private Double lateFee;
    private Double partialPayment;
    private Double dueAmount;
    private InvoiceStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
}
