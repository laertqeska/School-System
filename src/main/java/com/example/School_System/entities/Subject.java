package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

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


    public Subject(Long schoolId, String name, String code, String description, Boolean isActive) {
        this.schoolId = schoolId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public Long getSchoolId() { return schoolId; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}