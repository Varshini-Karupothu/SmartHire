package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.Interview;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.InterviewRepository;
import com.smarthire.smarthire_backend.repository.NotificationRepository;
import com.smarthire.smarthire_backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            UserRepository userRepository,
            NotificationRepository notificationRepository) {

        this.interviewRepository =
                interviewRepository;

        this.userRepository =
                userRepository;

        this.notificationRepository =
                notificationRepository;
    }

    public List<Interview> getInterviews(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        return interviewRepository
                .findByUser(user);
    }

    public Interview addInterview(
            String email,
            Interview interview) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        interview.setUser(user);

        return interviewRepository.save(
                interview
        );
    }

    @Transactional
    public void deleteInterview(
            String email,
            Long interviewId) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        Interview interview =
                interviewRepository
                        .findById(interviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview not found"
                                ));

        if (!interview.getUser()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Unauthorized interview access"
            );
        }

        notificationRepository
                .deleteByInterview(interview);

        interviewRepository.delete(interview);
    }
}