package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.SkillAnalysis;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.SkillAnalysisRepository;
import com.smarthire.smarthire_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillAnalysisService {

    private final SkillAnalysisRepository skillAnalysisRepository;
    private final UserRepository userRepository;

    public SkillAnalysisService(
            SkillAnalysisRepository skillAnalysisRepository,
            UserRepository userRepository) {

        this.skillAnalysisRepository = skillAnalysisRepository;
        this.userRepository = userRepository;
    }

    public SkillAnalysis analyzeSkills(
            String email,
            SkillAnalysis analysis) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<String> jobSkills = Arrays.stream(
                        analysis.getJobSkills().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(skill -> !skill.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        List<String> mySkills = Arrays.stream(
                        analysis.getMySkills().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(skill -> !skill.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        List<String> matched = jobSkills.stream()
                .filter(mySkills::contains)
                .collect(Collectors.toList());

        List<String> missing = jobSkills.stream()
                .filter(skill -> !mySkills.contains(skill))
                .collect(Collectors.toList());

        double percentage = 0;

        if (!jobSkills.isEmpty()) {
            percentage =
                    (matched.size() * 100.0)
                            / jobSkills.size();
        }

        analysis.setUser(user);

        analysis.setMatchedSkills(
                String.join(", ", matched)
        );

        analysis.setMissingSkills(
                String.join(", ", missing)
        );

        analysis.setMatchPercentage(
                Math.round(percentage * 100.0) / 100.0
        );

        return skillAnalysisRepository.save(analysis);
    }

    public List<SkillAnalysis> getAnalyses(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return skillAnalysisRepository.findByUser(user);
    }
}


