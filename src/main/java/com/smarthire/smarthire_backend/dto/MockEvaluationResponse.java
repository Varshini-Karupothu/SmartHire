package com.smarthire.smarthire_backend.dto;

import java.util.List;

public class MockEvaluationResponse {

    private int score;
    private List<QuestionResult> results;

    public MockEvaluationResponse() {
    }

    public MockEvaluationResponse(
            int score,
            List<QuestionResult> results) {

        this.score = score;
        this.results = results;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<QuestionResult> getResults() {
        return results;
    }

    public void setResults(List<QuestionResult> results) {
        this.results = results;
    }

    public static class QuestionResult {

        private Long questionId;
        private int score;
        private String feedback;
        private List<String> missingKeywords;

        public QuestionResult() {
        }

        public QuestionResult(
                Long questionId,
                int score,
                String feedback,
                List<String> missingKeywords) {

            this.questionId = questionId;
            this.score = score;
            this.feedback = feedback;
            this.missingKeywords = missingKeywords;
        }

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public List<String> getMissingKeywords() {
            return missingKeywords;
        }

        public void setMissingKeywords(
                List<String> missingKeywords) {

            this.missingKeywords = missingKeywords;
        }
    }
}

