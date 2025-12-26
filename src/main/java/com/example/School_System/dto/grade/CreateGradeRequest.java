package com.example.School_System.dto.grade;

import com.example.School_System.entities.valueObjects.GradeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class CreateGradeRequest {
    private Long studentId;
    private Long studyProgramSubjectId;
    private Long classId;
    private GradeType gradeType;
    private BigDecimal score;
    private BigDecimal maxScore;
    private LocalDate gradeDate;
    private String notes;

    public CreateGradeRequest() {
    }

    public CreateGradeRequest(Long studentId, Long studyProgramSubjectId, Long classId, GradeType gradeType, BigDecimal score, BigDecimal maxScore, LocalDate gradeDate,String notes) {
        this.studentId = studentId;
        this.studyProgramSubjectId = studyProgramSubjectId;
        this.classId = classId;
        this.gradeType = gradeType;
        this.score = score;
        this.maxScore = maxScore;
        this.notes = notes;
    }

    public LocalDate  getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate  gradeDate) {
        this.gradeDate = gradeDate;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudyProgramSubjectId() {
        return studyProgramSubjectId;
    }

    public void setStudyProgramSubjectId(Long studyProgramSubjectId) {
        this.studyProgramSubjectId = studyProgramSubjectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
