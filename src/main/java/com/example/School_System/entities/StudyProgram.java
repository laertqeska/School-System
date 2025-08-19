package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.DegreeLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_programs")
public class StudyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree_level", nullable = false)
    private DegreeLevel degreeLevel;

    @Column(name = "duration_semesters", nullable = false)
    private Integer durationSemesters;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public StudyProgram(Long departmentId, String name, DegreeLevel degreeLevel,
                        Integer durationSemesters, Integer totalCredits) {
        this.departmentId = departmentId;
        this.name = name;
        this.degreeLevel = degreeLevel;
        this.durationSemesters = durationSemesters;
        this.totalCredits = totalCredits;
    }

    public Long getId() { return id; }
    public Long getDepartmentId() { return departmentId; }
    public String getName() { return name; }
    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public Integer getDurationSemesters() { return durationSemesters; }
    public Integer getTotalCredits() { return totalCredits; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public void setName(String name) { this.name = name; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; }
    public void setDurationSemesters(Integer durationSemesters) { this.durationSemesters = durationSemesters; }
    public void setTotalCredits(Integer totalCredits) { this.totalCredits = totalCredits; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}