package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.entity.SkillAnalysis;
import com.smarthire.smarthire_backend.service.SkillAnalysisService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-analysis")
public class SkillAnalysisController {

    private final SkillAnalysisService skillAnalysisService;

    public SkillAnalysisController(
            SkillAnalysisService skillAnalysisService) {

        this.skillAnalysisService = skillAnalysisService;
    }

    @PostMapping
    public SkillAnalysis analyzeSkills(
            @RequestBody SkillAnalysis analysis,
            Authentication authentication) {

        String email = authentication.getName();

        return skillAnalysisService.analyzeSkills(
                email,
                analysis
        );
    }

    @GetMapping
    public List<SkillAnalysis> getAnalyses(
            Authentication authentication) {

        String email = authentication.getName();

        return skillAnalysisService.getAnalyses(email);
    }
}


