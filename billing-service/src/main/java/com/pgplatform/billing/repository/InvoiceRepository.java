package com.pgplatform.billing.repository;

import com.pgplatform.billing.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByHostelId(Long hostelId);

    List<Invoice> findByTenantId(Long tenantId);

    List<Invoice> findByGuestId(Long guestId);

    Optional<Invoice> findByGuestIdAndMonthAndYear(Long guestId, Integer month, Integer year);

    List<Invoice> findByHostelIdAndMonthAndYear(Long hostelId, Integer month, Integer year);
}
