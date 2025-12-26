package com.example.School_System.dto.grade;

import java.math.BigDecimal;

public class StudentGradeModel {
    private String subject;
    private int credits;
    private BigDecimal score;
    private BigDecimal maxScore;

    public StudentGradeModel() {
    }

    public StudentGradeModel(String subject, int credits, BigDecimal score, BigDecimal maxScore) {
        this.subject = subject;
        this.credits = credits;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }
}
