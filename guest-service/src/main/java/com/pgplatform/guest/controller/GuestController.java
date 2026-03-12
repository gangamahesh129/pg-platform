package com.pgplatform.guest.controller;

import com.pgplatform.guest.dto.GuestDto;
import com.pgplatform.guest.dto.RoomAssignmentDto;
import com.pgplatform.guest.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<GuestDto> addGuest(@RequestBody GuestDto dto) {
        return ResponseEntity.ok(guestService.addGuest(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getGuest(id));
    }

    @GetMapping("/hostel/{hostelId}")
    public ResponseEntity<List<GuestDto>> getGuestsByHostel(@PathVariable Long hostelId) {
        return ResponseEntity.ok(guestService.getGuestsByHostel(hostelId));
    }

    @PostMapping("/assign-room")
    public ResponseEntity<RoomAssignmentDto> assignRoom(@RequestBody RoomAssignmentDto dto) {
        return ResponseEntity.ok(guestService.assignRoom(dto));
    }

    @PutMapping("/assignments/{assignmentId}/checkout")
    public ResponseEntity<RoomAssignmentDto> checkOut(
            @PathVariable Long assignmentId,
            @RequestParam(required = false) LocalDate checkOutDate) {
        LocalDate date = checkOutDate != null ? checkOutDate : LocalDate.now();
        return ResponseEntity.ok(guestService.checkOut(assignmentId, date));
    }

    @GetMapping("/hostel/{hostelId}/assignments")
    public ResponseEntity<List<RoomAssignmentDto>> getAssignments(@PathVariable Long hostelId) {
        return ResponseEntity.ok(guestService.getAssignmentsByHostel(hostelId));
    }
}
