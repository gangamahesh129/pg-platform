package com.pgmanagement.repository;

import com.pgmanagement.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Long> {

    List<Floor> findByHostelId(Long hostelId);
}
