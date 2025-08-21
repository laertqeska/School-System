package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.GradeType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    @Column(name = "grade_type", nullable = false, length = 20)
    private GradeType gradeType;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "max_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxScore;

    @Column(name = "grade_date", nullable = false)
    private Date gradeDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Grade(Long studentId, Long subjectId, Long teacherId, Long classId, Long academicYearId, GradeType gradeType, BigDecimal score, BigDecimal maxScore, Date gradeDate, String notes) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.academicYearId = academicYearId;
        this.gradeType = gradeType;
        this.score = score;
        this.maxScore = maxScore;
        this.gradeDate = gradeDate;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getSubjectId() { return subjectId; }
    public Long getTeacherId() { return teacherId; }
    public Long getClassId() { return classId; }
    public Long getAcademicYearId() { return academicYearId; }
    public GradeType getGradeType() { return gradeType; }
    public BigDecimal getScore() { return score; }
    public BigDecimal getMaxScore() { return maxScore; }
    public Date getGradeDate() { return gradeDate; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public void setClassId(Long classId) { this.classId = classId; }
    public void setAcademicYearId(Long academicYearId) { this.academicYearId = academicYearId; }
    public void setGradeType(GradeType gradeType) { this.gradeType = gradeType; }
    public void setScore(BigDecimal score) { this.score = score; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    public void setGradeDate(Date gradeDate) { this.gradeDate = gradeDate; }
    public void setNotes(String notes) { this.notes = notes; }
}