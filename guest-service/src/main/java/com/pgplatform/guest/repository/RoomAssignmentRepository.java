package com.pgplatform.guest.repository;

import com.pgplatform.guest.entity.RoomAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, Long> {

    List<RoomAssignment> findByGuestId(Long guestId);

    List<RoomAssignment> findByHostelId(Long hostelId);

    Optional<RoomAssignment> findByGuestIdAndActiveTrue(Long guestId);

    List<RoomAssignment> findByHostelIdAndActiveTrue(Long hostelId);
}
