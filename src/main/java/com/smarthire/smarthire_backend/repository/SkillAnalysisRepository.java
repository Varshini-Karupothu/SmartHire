package com.smarthire.smarthire_backend.repository;

import com.smarthire.smarthire_backend.entity.SkillAnalysis;
import com.smarthire.smarthire_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillAnalysisRepository
        extends JpaRepository<SkillAnalysis, Long> {

    List<SkillAnalysis> findByUser(User user);

}


