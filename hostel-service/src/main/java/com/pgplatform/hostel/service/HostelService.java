package com.pgplatform.hostel.service;

import com.pgplatform.hostel.dto.HostelDto;
import com.pgplatform.hostel.dto.RoomDto;
import com.pgplatform.hostel.entity.Hostel;
import com.pgplatform.hostel.entity.Room;
import com.pgplatform.hostel.repository.HostelRepository;
import com.pgplatform.hostel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostelService {

    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;

    public HostelDto createHostel(HostelDto dto) {
        Hostel hostel = Hostel.builder()
                .tenantId(dto.getTenantId())
                .ownerId(dto.getOwnerId())
                .name(dto.getName())
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .pinCode(dto.getPinCode())
                .contactPhone(dto.getContactPhone())
                .contactEmail(dto.getContactEmail())
                .build();
        hostel = hostelRepository.save(hostel);
        return toHostelDto(hostel);
    }

    public HostelDto getHostel(Long id) {
        Hostel hostel = hostelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hostel not found: " + id));
        return toHostelDto(hostel);
    }

    public List<HostelDto> getHostelsByTenant(Long tenantId) {
        return hostelRepository.findByTenantId(tenantId).stream()
                .map(this::toHostelDto)
                .collect(Collectors.toList());
    }

    public List<HostelDto> getHostelsByOwner(Long ownerId) {
        return hostelRepository.findByOwnerId(ownerId).stream()
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
        return HostelDto.builder()
                .id(h.getId())
                .tenantId(h.getTenantId())
                .ownerId(h.getOwnerId())
                .name(h.getName())
                .street(h.getStreet())
                .city(h.getCity())
                .state(h.getState())
                .pinCode(h.getPinCode())
                .contactPhone(h.getContactPhone())
                .contactEmail(h.getContactEmail())
                .build();
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
