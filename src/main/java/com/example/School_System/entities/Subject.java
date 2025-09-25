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

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<StudyProgramSubject> studyProgramSubjects = new HashSet<>();

    @OneToMany(mappedBy = "subjectId", fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects = new HashSet<>();

    public Subject() {
    }


    public Subject(School school, String name, String code, String description, Boolean isActive) {
        this.school = school;
        this.name = name;
        this.code = code;
        this.description = description;
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
}