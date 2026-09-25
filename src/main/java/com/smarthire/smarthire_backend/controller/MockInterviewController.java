package com.smarthire.smarthire_backend.controller;

import com.smarthire.smarthire_backend.dto.MockAnswerRequest;
import com.smarthire.smarthire_backend.dto.MockEvaluationResponse;
import com.smarthire.smarthire_backend.entity.MockQuestion;
import com.smarthire.smarthire_backend.service.MockInterviewService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mock-interview")
@CrossOrigin(origins = "http://localhost:4200")
public class MockInterviewController {

    private final MockInterviewService mockInterviewService;

    public MockInterviewController(
            MockInterviewService mockInterviewService) {

        this.mockInterviewService = mockInterviewService;
    }

    @GetMapping("/questions")
    public List<MockQuestion> getQuestions(
            @RequestParam String role,
            @RequestParam String difficulty,
            @RequestParam String topic) {

        return mockInterviewService.getQuestions(
                role,
                difficulty,
                topic
        );
    }

    @GetMapping("/questions/all")
    public List<MockQuestion> getAllQuestions() {

        return mockInterviewService.getAllQuestions();
    }

    @PostMapping("/questions")
    public MockQuestion addQuestion(
            @RequestBody MockQuestion question) {

        return mockInterviewService.addQuestion(question);
    }

    @PostMapping("/evaluate")
    public MockEvaluationResponse evaluateAnswers(
            @RequestBody List<MockAnswerRequest> answers) {

        return mockInterviewService.evaluateAnswers(answers);
    }
}




