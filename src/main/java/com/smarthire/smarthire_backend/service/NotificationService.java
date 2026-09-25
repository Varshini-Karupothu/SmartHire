package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.entity.Notification;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.NotificationRepository;
import com.smarthire.smarthire_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository) {

        this.notificationRepository =
                notificationRepository;

        this.userRepository =
                userRepository;
    }

    public List<Notification> getNotifications(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user);
    }

    public Notification createNotification(
            String email,
            String title,
            String message) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        Notification notification =
                new Notification();

        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(
                LocalDateTime.now()
        );

        return notificationRepository.save(
                notification
        );
    }

    public Notification createInterviewNotification(
            String email,
            Interview interview,
            String title,
            String message) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        Notification notification =
                new Notification();

        notification.setUser(user);
        notification.setInterview(interview);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(
                LocalDateTime.now()
        );

        return notificationRepository.save(
                notification
        );
    }

    public Notification markAsRead(
            String email,
            Long notificationId) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        if (!notification.getUser()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Unauthorized notification access"
            );
        }

        notification.setRead(true);

        return notificationRepository.save(
                notification
        );
    }
}