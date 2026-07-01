package com.pgmanagement.repository;

import com.pgmanagement.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByHostelId(Long hostelId);

    List<Guest> findByTenantId(Long tenantId);

    List<Guest> findByHostelIdAndTenantId(Long hostelId, Long tenantId);
}
