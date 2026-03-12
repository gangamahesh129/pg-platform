package com.pgplatform.guest.service;

import com.pgplatform.guest.dto.GuestDto;
import com.pgplatform.guest.dto.RoomAssignmentDto;
import com.pgplatform.guest.entity.Guest;
import com.pgplatform.guest.entity.RoomAssignment;
import com.pgplatform.guest.repository.GuestRepository;
import com.pgplatform.guest.repository.RoomAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final RoomAssignmentRepository assignmentRepository;

    public GuestDto addGuest(GuestDto dto) {
        Guest guest = Guest.builder()
                .hostelId(dto.getHostelId())
                .tenantId(dto.getTenantId())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .idProof(dto.getIdProof())
                .emergencyContact(dto.getEmergencyContact())
                .address(dto.getAddress())
                .build();
        guest = guestRepository.save(guest);
        return toGuestDto(guest);
    }

    public GuestDto getGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found: " + id));
        return toGuestDto(guest);
    }

    public List<GuestDto> getGuestsByHostel(Long hostelId) {
        return guestRepository.findByHostelId(hostelId).stream()
                .map(this::toGuestDto)
                .collect(Collectors.toList());
    }

    public RoomAssignmentDto assignRoom(RoomAssignmentDto dto) {
        RoomAssignment assignment = RoomAssignment.builder()
                .guestId(dto.getGuestId())
                .roomId(dto.getRoomId())
                .hostelId(dto.getHostelId())
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .monthlyRent(dto.getMonthlyRent())
                .active(true)
                .build();
        assignment = assignmentRepository.save(assignment);
        return toAssignmentDto(assignment);
    }

    public RoomAssignmentDto checkOut(Long assignmentId, LocalDate checkOutDate) {
        RoomAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found: " + assignmentId));
        assignment.setCheckOutDate(checkOutDate);
        assignment.setActive(false);
        assignment = assignmentRepository.save(assignment);
        return toAssignmentDto(assignment);
    }

    public List<RoomAssignmentDto> getAssignmentsByHostel(Long hostelId) {
        return assignmentRepository.findByHostelIdAndActiveTrue(hostelId).stream()
                .map(this::toAssignmentDto)
                .collect(Collectors.toList());
    }

    private GuestDto toGuestDto(Guest g) {
        return GuestDto.builder()
                .id(g.getId())
                .hostelId(g.getHostelId())
                .tenantId(g.getTenantId())
                .name(g.getName())
                .phoneNumber(g.getPhoneNumber())
                .email(g.getEmail())
                .idProof(g.getIdProof())
                .emergencyContact(g.getEmergencyContact())
                .address(g.getAddress())
                .createdAt(g.getCreatedAt())
                .build();
    }

    private RoomAssignmentDto toAssignmentDto(RoomAssignment a) {
        return RoomAssignmentDto.builder()
                .id(a.getId())
                .guestId(a.getGuestId())
                .roomId(a.getRoomId())
                .hostelId(a.getHostelId())
                .checkInDate(a.getCheckInDate())
                .checkOutDate(a.getCheckOutDate())
                .monthlyRent(a.getMonthlyRent())
                .active(a.getActive())
                .build();
    }
}
