package com.pgplatform.hostel.repository;

import com.pgplatform.hostel.entity.Hostel;
import com.pgplatform.hostel.entity.HostelPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelPermissionRepositary extends JpaRepository<HostelPermission, Long> {
    List<Hostel> findByOwnerId(Long ownerId);

}
