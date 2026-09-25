package com.smarthire.smarthire_backend.repository;

import com.smarthire.smarthire_backend.entity.MockQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockQuestionRepository
        extends JpaRepository<MockQuestion, Long> {

    List<MockQuestion> findByRoleAndDifficultyAndTopic(
            String role,
            String difficulty,
            String topic
    );
}
