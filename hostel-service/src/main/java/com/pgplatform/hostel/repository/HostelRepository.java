package com.pgplatform.hostel.repository;

import com.pgplatform.hostel.entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> findByTenantId(Long tenantId);

    List<Hostel> findByOwnerId(Long ownerId);

    List<Hostel> findByTenantIdAndOwnerId(Long tenantId, Long ownerId);
}
