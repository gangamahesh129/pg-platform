package com.pgplatform.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueReportDto {

    private Long hostelId;
    private Integer month;
    private Integer year;
    private Double totalRevenue;
    private Double totalPending;
    private Long paidInvoicesCount;
    private Long pendingInvoicesCount;
}
