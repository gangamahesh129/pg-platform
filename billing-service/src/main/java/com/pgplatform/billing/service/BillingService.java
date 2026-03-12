package com.pgplatform.billing.service;

import com.pgplatform.billing.dto.InvoiceDto;
import com.pgplatform.billing.dto.RevenueReportDto;
import com.pgplatform.billing.entity.Invoice;
import com.pgplatform.billing.entity.Payment;
import com.pgplatform.billing.repository.InvoiceRepository;
import com.pgplatform.billing.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public InvoiceDto createInvoice(InvoiceDto dto) {
        Invoice invoice = Invoice.builder()
                .tenantId(dto.getTenantId())
                .hostelId(dto.getHostelId())
                .guestId(dto.getGuestId())
                .assignmentId(dto.getAssignmentId())
                .month(dto.getMonth())
                .year(dto.getYear())
                .totalAmount(dto.getTotalAmount())
                .lateFee(dto.getLateFee() != null ? dto.getLateFee() : 0.0)
                .partialPayment(dto.getPartialPayment() != null ? dto.getPartialPayment() : 0.0)
                .dueAmount(dto.getTotalAmount() - (dto.getPartialPayment() != null ? dto.getPartialPayment() : 0.0))
                .status(dto.getStatus() != null ? dto.getStatus() : Invoice.InvoiceStatus.PENDING)
                .dueDate(dto.getDueDate() != null ? dto.getDueDate() : LocalDate.now().plusDays(5))
                .build();
        invoice = invoiceRepository.save(invoice);
        return toInvoiceDto(invoice);
    }

    public InvoiceDto recordPayment(Long invoiceId, Double amount, Payment.PaymentMethod method) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceId));

        Payment payment = Payment.builder()
                .invoiceId(invoiceId)
                .amount(amount)
                .method(method)
                .build();
        paymentRepository.save(payment);

        double newPartial = (invoice.getPartialPayment() != null ? invoice.getPartialPayment() : 0) + amount;
        invoice.setPartialPayment(newPartial);
        invoice.setDueAmount(invoice.getTotalAmount() - newPartial);
        invoice.setStatus(newPartial >= invoice.getTotalAmount() ? Invoice.InvoiceStatus.PAID : Invoice.InvoiceStatus.PARTIAL);
        invoice = invoiceRepository.save(invoice);
        return toInvoiceDto(invoice);
    }

    public List<InvoiceDto> getInvoicesByHostel(Long hostelId) {
        return invoiceRepository.findByHostelId(hostelId).stream()
                .map(this::toInvoiceDto)
                .collect(Collectors.toList());
    }

    public List<InvoiceDto> getInvoicesByHostelAndMonth(Long hostelId, Integer month, Integer year) {
        return invoiceRepository.findByHostelIdAndMonthAndYear(hostelId, month, year).stream()
                .map(this::toInvoiceDto)
                .collect(Collectors.toList());
    }

    public RevenueReportDto getRevenueReport(Long hostelId, Integer month, Integer year) {
        List<Invoice> invoices = invoiceRepository.findByHostelIdAndMonthAndYear(hostelId, month, year);
        double totalRevenue = invoices.stream()
                .filter(i -> i.getStatus() == Invoice.InvoiceStatus.PAID || i.getStatus() == Invoice.InvoiceStatus.PARTIAL)
                .mapToDouble(i -> i.getPartialPayment() != null ? i.getPartialPayment() : 0)
                .sum();
        double totalPending = invoices.stream()
                .filter(i -> i.getStatus() != Invoice.InvoiceStatus.PAID)
                .mapToDouble(Invoice::getDueAmount)
                .sum();
        long paidCount = invoices.stream().filter(i -> i.getStatus() == Invoice.InvoiceStatus.PAID).count();
        long pendingCount = invoices.stream().filter(i -> i.getStatus() != Invoice.InvoiceStatus.PAID).count();

        return RevenueReportDto.builder()
                .hostelId(hostelId)
                .month(month)
                .year(year)
                .totalRevenue(totalRevenue)
                .totalPending(totalPending)
                .paidInvoicesCount(paidCount)
                .pendingInvoicesCount(pendingCount)
                .build();
    }

    private InvoiceDto toInvoiceDto(Invoice i) {
        return InvoiceDto.builder()
                .id(i.getId())
                .tenantId(i.getTenantId())
                .hostelId(i.getHostelId())
                .guestId(i.getGuestId())
                .assignmentId(i.getAssignmentId())
                .month(i.getMonth())
                .year(i.getYear())
                .totalAmount(i.getTotalAmount())
                .lateFee(i.getLateFee())
                .partialPayment(i.getPartialPayment())
                .dueAmount(i.getDueAmount())
                .status(i.getStatus())
                .dueDate(i.getDueDate())
                .createdAt(i.getCreatedAt())
                .build();
    }
}
