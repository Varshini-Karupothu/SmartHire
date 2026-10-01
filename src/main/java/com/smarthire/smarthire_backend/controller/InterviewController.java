package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.service.InterviewService;
import com.smarthire.smarthire_backend.service.NotificationService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final NotificationService notificationService;

    public InterviewController(
            InterviewService interviewService,
            NotificationService notificationService) {

        this.interviewService =
                interviewService;

        this.notificationService =
                notificationService;
    }

    @GetMapping
    public List<Interview> getInterviews(
            Authentication authentication) {

        String email =
                authentication.getName();

        return interviewService
                .getInterviews(email);
    }

    @PostMapping
    public Interview addInterview(
            @RequestBody Interview interview,
            Authentication authentication) {

        String email =
                authentication.getName();

        Interview savedInterview =
                interviewService.addInterview(
                        email,
                        interview
                );

        String title =
                "Interview Scheduled";

        String message =
                "Your " +
                savedInterview.getRole() +
                " " +
                savedInterview.getRound() +
                " at " +
                savedInterview.getCompany() +
                " is scheduled on " +
                savedInterview.getDate() +
                " at " +
                savedInterview.getTime() +
                ".";

        notificationService.createInterviewNotification(
                email,
                savedInterview,
                title,
                message
        );

        return savedInterview;
    }

    @DeleteMapping("/{id}")
    public String deleteInterview(
            @PathVariable Long id,
            Authentication authentication) {

        String email =
                authentication.getName();

        interviewService.deleteInterview(
                email,
                id
        );

        return "Interview deleted successfully";
    }
}
