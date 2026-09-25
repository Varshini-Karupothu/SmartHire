package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.entity.JobApplication;
import com.smarthire.smarthire_backend.service.JobApplicationService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:4200")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(
            JobApplicationService jobApplicationService) {

        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public List<JobApplication> getApplications(
            Authentication authentication) {

        String email = authentication.getName();

        System.out.println(
                "Authenticated email: " + email
        );

        return jobApplicationService.getApplications(email);
    }

    @PostMapping
    public JobApplication addApplication(
            @RequestBody JobApplication application,
            Authentication authentication) {

        String email = authentication.getName();

        System.out.println(
                "Authenticated email: " + email
        );

        return jobApplicationService.addApplication(
                email,
                application
        );
    }

    @PutMapping("/{id}/status")
    public JobApplication updateApplicationStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        String email = authentication.getName();

        String status = request.get("status");

        return jobApplicationService.updateApplicationStatus(
                email,
                id,
                status
        );
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        jobApplicationService.deleteApplication(
                email,
                id
        );

        return "Application deleted successfully";
    }
}

