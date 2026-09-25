package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.entity.Resume;
import com.smarthire.smarthire_backend.entity.User;
import com.smarthire.smarthire_backend.repository.ResumeRepository;
import com.smarthire.smarthire_backend.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public Resume getResume(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return resumeRepository.findByUser(user)
                .orElse(null);
    }

    public Resume saveResume(
            String email,
            Resume resume) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Resume existingResume =
                resumeRepository.findByUser(user)
                        .orElse(null);

        if (existingResume != null) {

            existingResume.setName(resume.getName());
            existingResume.setEmail(resume.getEmail());
            existingResume.setPhone(resume.getPhone());
            existingResume.setLocation(resume.getLocation());
            existingResume.setSummary(resume.getSummary());
            existingResume.setEducation(resume.getEducation());
            existingResume.setSkills(resume.getSkills());
            existingResume.setProjects(resume.getProjects());
            existingResume.setCertifications(resume.getCertifications());
            existingResume.setExperience(resume.getExperience());

            return resumeRepository.save(existingResume);
        }

        resume.setUser(user);

        return resumeRepository.save(resume);
    }
}
