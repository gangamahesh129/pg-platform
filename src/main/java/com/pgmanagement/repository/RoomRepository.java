package com.pgmanagement.repository;

import com.pgmanagement.entity.Room;
import com.pgmanagement.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHostelId(Long hostelId);

    List<Room> findByHostelIdAndStatus(Long hostelId, RoomStatus status);

    List<Room> findByFloorId(Long floorId);
}
