package com.smarthire.smarthire_backend.dto;

public class MockAnswerRequest {

    private Long questionId;
    private String answer;

    public MockAnswerRequest() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}


