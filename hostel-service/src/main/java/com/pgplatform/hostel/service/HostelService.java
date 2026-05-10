package com.pgplatform.hostel.service;

import com.pgplatform.hostel.dto.HostelDto;
import com.pgplatform.hostel.dto.RoomDto;
import com.pgplatform.hostel.entity.Hostel;

import com.pgplatform.hostel.entity.Room;
import com.pgplatform.hostel.repository.HostelPermissionRepositary;
import com.pgplatform.hostel.repository.HostelRepository;
import com.pgplatform.hostel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostelService {

    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;
    private final HostelPermissionRepositary hostelPermissionRepositary;

    public HostelDto createHostel(HostelDto dto) {

        dto.setTenantId(getTeantId());
        dto.getPermissions().forEach(hostelPermission -> {
            hostelPermission.setTeantId(getTeantId());
        });
        Hostel hostel = new Hostel();
        BeanUtils.copyProperties(dto, hostel);

        hostel.setTenantId(getTeantId());
        hostel.getPermissions().forEach(hostelPermission -> {
            hostelPermission.setTeantId(getTeantId());
        });

        hostel = hostelRepository.save(hostel);

        dto.setId(hostel.getId());
        dto.getHostelAddress().setId(hostel.getHostelAddress().getId());

        return toHostelDto(hostel);
    }

    private static String getTeantId() {
        return "HSTL" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public HostelDto getHostel(Long id) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found: " + id));
        return toHostelDto(hostel);
    }

    public List<HostelDto> getHostelsByTenant(String tenantId) {
        return hostelRepository.findByTenantId(tenantId).stream()
                .map(this::toHostelDto)
                .collect(Collectors.toList());
    }

    public List<HostelDto> getHostelsByOwner(Long ownerId) {
        return hostelPermissionRepositary.findByOwnerId(ownerId).stream()
                .map(this::toHostelDto)
                .collect(Collectors.toList());
    }

    public RoomDto createRoom(RoomDto dto) {
        Room room = Room.builder()
                .hostelId(dto.getHostelId())
                .floorId(dto.getFloorId())
                .roomNumber(dto.getRoomNumber())
                .capacity(dto.getCapacity() != null ? dto.getCapacity() : 1)
                .monthlyRent(dto.getMonthlyRent())
                .status(dto.getStatus() != null ? dto.getStatus() : Room.RoomStatus.AVAILABLE)
                .build();
        room = roomRepository.save(room);
        return toRoomDto(room);
    }

    public List<RoomDto> getRoomsByHostel(Long hostelId) {
        return roomRepository.findByHostelId(hostelId).stream()
                .map(this::toRoomDto)
                .collect(Collectors.toList());
    }

    public List<RoomDto> getAvailableRooms(Long hostelId) {
        return roomRepository.findByHostelIdAndStatus(hostelId, Room.RoomStatus.AVAILABLE).stream()
                .map(this::toRoomDto)
                .collect(Collectors.toList());
    }

    public RoomDto getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found: " + id));
        return toRoomDto(room);
    }

    private HostelDto toHostelDto(Hostel h) {

        HostelDto dto = new HostelDto();
        BeanUtils.copyProperties(h, dto);

        return dto;
    }

    private RoomDto toRoomDto(Room r) {
        return RoomDto.builder()
                .id(r.getId())
                .hostelId(r.getHostelId())
                .floorId(r.getFloorId())
                .roomNumber(r.getRoomNumber())
                .capacity(r.getCapacity())
                .monthlyRent(r.getMonthlyRent())
                .status(r.getStatus())
                .build();
    }
}
