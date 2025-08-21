package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_program_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_program_id", "subject_id"}))
public class StudyProgramSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_program_id", nullable = false)
    private Long studyProgramId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer semester;

    @Column(name = "year_level", nullable = false)
    private Integer yearLevel;

    @Column(columnDefinition = "JSON")
    private String prerequisites;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public StudyProgramSubject(Long studyProgramId, Long subjectId, Integer credits, Integer semester, Integer yearLevel, String prerequisites, Boolean isActive) {
        this.studyProgramId = studyProgramId;
        this.subjectId = subjectId;
        this.credits = credits;
        this.semester = semester;
        this.yearLevel = yearLevel;
        this.prerequisites = prerequisites;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public Long getStudyProgramId() { return studyProgramId; }
    public Long getSubjectId() { return subjectId; }
    public Integer getCredits() { return credits; }
    public Integer getSemester() { return semester; }
    public Integer getYearLevel() { return yearLevel; }
    public String getPrerequisites() { return prerequisites; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudyProgramId(Long studyProgramId) { this.studyProgramId = studyProgramId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
    public void setPrerequisites(String prerequisites) { this.prerequisites = prerequisites; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
