package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_subjects_school"))
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_subjects_department")
    )
    private Department department;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false,
        foreignKey = @ForeignKey(name = "fk_subject_created_by")
    )
    private User createdBy;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<StudyProgramSubject> studyProgramSubjects = new HashSet<>();

    public Subject() {
    }

    public Subject(School school, String name, String code, String description,Department department, Boolean isActive) {
        this.school = school;
        this.name = name;
        this.code = code;
        this.description = description;
        this.department = department;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public School getSchool() { return school; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setSchool(School school) { this.school = school; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<StudyProgramSubject> getStudyProgramSubjects() {
        return studyProgramSubjects;
    }

    public void setStudyProgramSubjects(Set<StudyProgramSubject> studyProgramSubjects) {
        this.studyProgramSubjects = studyProgramSubjects;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}