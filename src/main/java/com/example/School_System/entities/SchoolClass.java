package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_program_id", nullable = false)
    private Long studyProgramId;

    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "year_level", nullable = false)
    private Integer yearLevel;

    @Column(name = "max_students")
    private Integer maxStudents = 30;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SchoolClass(Long studyProgramId,Long academicYearId,String name,Integer yearLevel, Integer maxStudents) {
        this.studyProgramId = studyProgramId;
        this.academicYearId = academicYearId;
        this.name = name;
        this.yearLevel = yearLevel;
        this.maxStudents = maxStudents;

    }

    public Long getId() { return id; }
    public Long getStudyProgramId() { return studyProgramId; }
    public Long getAcademicYearId() { return academicYearId; }
    public String getName() { return name; }
    public Integer getYearLevel() { return yearLevel; }
    public Integer getMaxStudents() { return maxStudents; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudyProgramId(Long studyProgramId) { this.studyProgramId = studyProgramId; }
    public void setAcademicYearId(Long academicYearId) { this.academicYearId = academicYearId; }
    public void setName(String name) { this.name = name; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
}