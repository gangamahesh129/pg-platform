package com.pgplatform.hostel.repository;

import com.pgplatform.hostel.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Long> {

    List<Floor> findByHostelId(Long hostelId);
}
