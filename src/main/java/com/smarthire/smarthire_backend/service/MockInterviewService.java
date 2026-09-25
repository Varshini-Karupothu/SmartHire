package com.smarthire.smarthire_backend.service;

import com.smarthire.smarthire_backend.dto.MockAnswerRequest;
import com.smarthire.smarthire_backend.dto.MockEvaluationResponse;
import com.smarthire.smarthire_backend.entity.MockQuestion;
import com.smarthire.smarthire_backend.repository.MockQuestionRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockInterviewService {

    private final MockQuestionRepository mockQuestionRepository;

    public MockInterviewService(
            MockQuestionRepository mockQuestionRepository) {

        this.mockQuestionRepository = mockQuestionRepository;
    }

    public List<MockQuestion> getQuestions(
            String role,
            String difficulty,
            String topic) {

        return mockQuestionRepository
                .findByRoleAndDifficultyAndTopic(
                        role,
                        difficulty,
                        topic
                );
    }

    public MockQuestion addQuestion(
            MockQuestion question) {

        return mockQuestionRepository.save(question);
    }

    public List<MockQuestion> getAllQuestions() {

        return mockQuestionRepository.findAll();
    }

    public MockEvaluationResponse evaluateAnswers(
            List<MockAnswerRequest> answers) {

        if (answers == null || answers.isEmpty()) {

            return new MockEvaluationResponse(
                    0,
                    new ArrayList<>()
            );
        }

        List<MockEvaluationResponse.QuestionResult> results =
                new ArrayList<>();

        int totalScore = 0;
        int evaluatedQuestions = 0;

        for (MockAnswerRequest submittedAnswer : answers) {

            MockQuestion question =
                    mockQuestionRepository
                            .findById(
                                    submittedAnswer.getQuestionId()
                            )
                            .orElse(null);

            if (question == null) {
                continue;
            }

            String userAnswer =
                    submittedAnswer.getAnswer();

            String keywords =
                    question.getExpectedKeywords();

            List<String> missingKeywords =
                    new ArrayList<>();

            int questionScore = 0;

            if (userAnswer == null ||
                    userAnswer.trim().isEmpty()) {

                if (keywords != null) {

                    String[] expectedKeywords =
                            keywords.toLowerCase().split(",");

                    for (String keyword : expectedKeywords) {

                        keyword = keyword.trim();

                        if (!keyword.isEmpty()) {
                            missingKeywords.add(keyword);
                        }
                    }
                }

                results.add(
                        new MockEvaluationResponse.QuestionResult(
                                question.getId(),
                                0,
                                "No answer provided.",
                                missingKeywords
                        )
                );

                evaluatedQuestions++;
                continue;
            }

            if (keywords == null ||
                    keywords.trim().isEmpty()) {

                results.add(
                        new MockEvaluationResponse.QuestionResult(
                                question.getId(),
                                0,
                                "No evaluation keywords available.",
                                missingKeywords
                        )
                );

                evaluatedQuestions++;
                continue;
            }

            String lowerAnswer =
                    userAnswer.toLowerCase();

            String[] expectedKeywords =
                    keywords.toLowerCase().split(",");

            int matchedKeywords = 0;

            for (String keyword : expectedKeywords) {

                keyword = keyword.trim();

                if (keyword.isEmpty()) {
                    continue;
                }

                if (lowerAnswer.contains(keyword)) {

                    matchedKeywords++;

                } else {

                    missingKeywords.add(keyword);
                }
            }

            int totalKeywords = expectedKeywords.length;

            if (totalKeywords > 0) {

                questionScore =
                        (matchedKeywords * 100)
                                / totalKeywords;
            }

            String feedback;

            if (questionScore >= 80) {

                feedback =
                        "Excellent answer! You covered most of the important concepts.";

            } else if (questionScore >= 50) {

                feedback =
                        "Good attempt, but some important concepts are missing.";

            } else if (questionScore > 0) {

                feedback =
                        "Your answer contains a few relevant concepts, but needs improvement.";

            } else {

                feedback =
                        "The answer does not contain the expected concepts.";
            }

            results.add(
                    new MockEvaluationResponse.QuestionResult(
                            question.getId(),
                            questionScore,
                            feedback,
                            missingKeywords
                    )
            );

            totalScore += questionScore;
            evaluatedQuestions++;
        }

        int overallScore = 0;

        if (evaluatedQuestions > 0) {

            overallScore =
                    Math.round(
                            (float) totalScore
                                    / evaluatedQuestions
                    );
        }

        return new MockEvaluationResponse(
                overallScore,
                results
        );
    }
}




