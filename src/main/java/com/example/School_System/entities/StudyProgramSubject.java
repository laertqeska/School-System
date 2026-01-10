package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "study_program_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"study_program_id", "subject_id"}))
@Filter(name = "deletedFilter",condition = "is_deleted = :isDeleted")
public class StudyProgramSubject extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_program_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_study_program_subjects_program"))
    private StudyProgram studyProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_study_program_subjects_subject"))
    private Subject subject;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer semester;

    @Column(name = "year_level", nullable = false)
    private Integer yearLevel;
//
//    @Column(columnDefinition = "JSON")
//    private String prerequisites;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by",nullable = false,
        foreignKey = @ForeignKey(name="fk_study_program_subject_created_by")
    )
    private User createdBy;

    @OneToMany(mappedBy = "studyProgramSubject", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "studyProgramSubject", fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects = new HashSet<>();




    public StudyProgramSubject(StudyProgram studyProgram, Subject subject, Integer credits, Integer semester, Integer yearLevel, Boolean isActive) {
        this.studyProgram = studyProgram;
        this.subject = subject;
        this.credits = credits;
        this.semester = semester;
        this.yearLevel = yearLevel;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public StudyProgram getStudyProgram() { return studyProgram; }
    public Subject getSubject() { return subject; }

    public StudyProgramSubject() {
    }

    public Integer getCredits() { return credits; }
    public Integer getSemester() { return semester; }
    public Integer getYearLevel() { return yearLevel; }
//    public String getPrerequisites() { return prerequisites; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudyProgram(StudyProgram studyProgram) { this.studyProgram = studyProgram; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
//    public void setPrerequisites(String prerequisites) { this.prerequisites = prerequisites; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
