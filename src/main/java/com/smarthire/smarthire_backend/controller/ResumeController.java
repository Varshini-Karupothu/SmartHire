package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.entity.Resume;
import com.smarthire.smarthire_backend.service.ResumeService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public Resume getResume(
            Authentication authentication) {

        String email = authentication.getName();

        return resumeService.getResume(email);
    }

    @PostMapping
    public Resume saveResume(
            @RequestBody Resume resume,
            Authentication authentication) {

        String email = authentication.getName();

        return resumeService.saveResume(
                email,
                resume
        );
    }
}
