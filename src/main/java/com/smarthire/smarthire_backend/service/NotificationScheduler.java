package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.entity.Notification;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.InterviewRepository;
import com.smarthire.smarthire_backend.repository.NotificationRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationScheduler {

    private final InterviewRepository interviewRepository;
    private final NotificationRepository notificationRepository;

    public NotificationScheduler(
            InterviewRepository interviewRepository,
            NotificationRepository notificationRepository) {

        this.interviewRepository =
                interviewRepository;

        this.notificationRepository =
                notificationRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void createInterviewReminders() {

        LocalDate tomorrow =
                LocalDate.now().plusDays(1);

        List<Interview> interviews =
                interviewRepository.findAll();

        for (Interview interview : interviews) {

            if (interview.getDate() == null) {
                continue;
            }

            if (!interview.getDate()
                    .equals(tomorrow.toString())) {
                continue;
            }

            User user =
                    interview.getUser();

            if (user == null) {
                continue;
            }

            String message =
                    "Your " +
                    interview.getRole() +
                    " " +
                    interview.getRound() +
                    " at " +
                    interview.getCompany() +
                    " is tomorrow at " +
                    interview.getTime() +
                    ".";

            List<Notification> notifications =
                    notificationRepository
                            .findByUserOrderByCreatedAtDesc(
                                    user
                            );

            boolean alreadyExists =
                    notifications.stream()
                            .anyMatch(
                                    notification ->
                                            notification.getTitle()
                                                    .equals(
                                                            "Interview Reminder"
                                                    )
                                            &&
                                            notification.getMessage()
                                                    .equals(message)
                            );

            if (alreadyExists) {
                continue;
            }

            Notification notification =
                    new Notification();

            notification.setUser(user);

            notification.setInterview(
                    interview
            );

            notification.setTitle(
                    "Interview Reminder"
            );

            notification.setMessage(
                    message
            );

            notification.setRead(false);

            notification.setCreatedAt(
                    LocalDateTime.now()
            );

            notificationRepository.save(
                    notification
            );
        }
    }
}