package com.example.School_System.dto.grade;

import java.math.BigDecimal;

public class TeachersGradeModel {
    private String studentFirstName;
    private String studentLastName;
    private String className;
    private BigDecimal score;
    private BigDecimal maxScore;

    public TeachersGradeModel() {
    }

    public TeachersGradeModel(String studentFirstName, String studentLastName, String className, BigDecimal score, BigDecimal maxScore) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.className = className;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
