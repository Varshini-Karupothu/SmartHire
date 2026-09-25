package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.entity.Notification;
import com.smarthire.smarthire_backend.service.NotificationService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @GetMapping
    public List<Notification> getNotifications(
            Authentication authentication) {

        String email = authentication.getName();

        return notificationService
                .getNotifications(email);
    }

    @PostMapping
    public Notification createNotification(
            @RequestParam String title,
            @RequestParam String message,
            Authentication authentication) {

        String email = authentication.getName();

        return notificationService.createNotification(
                email,
                title,
                message
        );
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return notificationService.markAsRead(
                email,
                id
        );
    }
}
