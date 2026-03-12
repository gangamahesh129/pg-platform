package com.pgplatform.notification.controller;

import com.pgplatform.notification.dto.NotificationRequest;
import com.pgplatform.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> send(@RequestBody NotificationRequest request) {
        notificationService.send(request);
        return ResponseEntity.ok(Map.of("status", "sent"));
    }

    @PostMapping("/rent-reminder")
    public ResponseEntity<Map<String, String>> sendRentReminder(
            @RequestParam String phoneNumber,
            @RequestParam String guestName,
            @RequestParam Double amount,
            @RequestParam String dueDate) {
        notificationService.sendRentReminder(phoneNumber, guestName, amount, dueDate);
        return ResponseEntity.ok(Map.of("status", "sent"));
    }
}
