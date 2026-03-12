package com.pgplatform.hostel.controller;

import com.pgplatform.hostel.dto.HostelDto;
import com.pgplatform.hostel.dto.RoomDto;
import com.pgplatform.hostel.service.HostelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostels")
@RequiredArgsConstructor
public class HostelController {

    private final HostelService hostelService;

    @PostMapping
    public ResponseEntity<HostelDto> createHostel(@RequestBody HostelDto dto) {
        return ResponseEntity.ok(hostelService.createHostel(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HostelDto> getHostel(@PathVariable Long id) {
        return ResponseEntity.ok(hostelService.getHostel(id));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<HostelDto>> getHostelsByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(hostelService.getHostelsByTenant(tenantId));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<HostelDto>> getHostelsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(hostelService.getHostelsByOwner(ownerId));
    }

    @PostMapping("/rooms")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(hostelService.createRoom(dto));
    }

    @GetMapping("/{hostelId}/rooms")
    public ResponseEntity<List<RoomDto>> getRooms(@PathVariable Long hostelId) {
        return ResponseEntity.ok(hostelService.getRoomsByHostel(hostelId));
    }

    @GetMapping("/{hostelId}/rooms/available")
    public ResponseEntity<List<RoomDto>> getAvailableRooms(@PathVariable Long hostelId) {
        return ResponseEntity.ok(hostelService.getAvailableRooms(hostelId));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(hostelService.getRoom(roomId));
    }
}
