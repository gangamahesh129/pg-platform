package com.pgmanagement.repository;

import com.pgmanagement.entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> findByTenantId(Long tenantId);

    List<Hostel> findByOwner_Id(Long ownerId);

    List<Hostel> findByTenantIdAndOwner_Id(Long tenantId, Long ownerId);
}
