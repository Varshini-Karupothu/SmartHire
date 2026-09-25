package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.JobApplication;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.JobApplicationRepository;
import com.smarthire.smarthire_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            UserRepository userRepository) {

        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    public List<JobApplication> getApplications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return jobApplicationRepository.findByUser(user);
    }

    public JobApplication addApplication(
            String email,
            JobApplication application) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        application.setUser(user);

        return jobApplicationRepository.save(application);
    }

    public void deleteApplication(
            String email,
            Long applicationId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        JobApplication application =
                jobApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        jobApplicationRepository.delete(application);
    }

    public JobApplication updateApplicationStatus(
            String email,
            Long applicationId,
            String status) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        JobApplication application =
                jobApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException("Application not found"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        application.setStatus(status);

        return jobApplicationRepository.save(application);
    }
}