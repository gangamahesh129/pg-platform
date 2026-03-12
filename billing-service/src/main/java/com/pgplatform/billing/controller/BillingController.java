package com.pgplatform.billing.controller;

import com.pgplatform.billing.dto.InvoiceDto;
import com.pgplatform.billing.dto.RevenueReportDto;
import com.pgplatform.billing.entity.Payment;
import com.pgplatform.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto dto) {
        return ResponseEntity.ok(billingService.createInvoice(dto));
    }

    @PostMapping("/invoices/{invoiceId}/payment")
    public ResponseEntity<InvoiceDto> recordPayment(
            @PathVariable Long invoiceId,
            @RequestParam Double amount,
            @RequestParam Payment.PaymentMethod method) {
        return ResponseEntity.ok(billingService.recordPayment(invoiceId, amount, method));
    }

    @GetMapping("/hostel/{hostelId}/invoices")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByHostel(@PathVariable Long hostelId) {
        return ResponseEntity.ok(billingService.getInvoicesByHostel(hostelId));
    }

    @GetMapping("/hostel/{hostelId}/revenue")
    public ResponseEntity<RevenueReportDto> getRevenueReport(
            @PathVariable Long hostelId,
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return ResponseEntity.ok(billingService.getRevenueReport(hostelId, month, year));
    }
}
