package com.pgplatform.hostel.repository;

import com.pgplatform.hostel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHostelId(Long hostelId);

    List<Room> findByHostelIdAndStatus(Long hostelId, Room.RoomStatus status);

    List<Room> findByFloorId(Long floorId);
}
