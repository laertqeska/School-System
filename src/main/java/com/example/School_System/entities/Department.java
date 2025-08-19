package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;

    @Column(nullable = false)
    private String name;

    @Column(name = "department_head_id")
    private Long departmentHeadId;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Department(Long facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
    }

    public Long getId() { return id; }
    public Long getFacultyId() { return facultyId; }
    public String getName() { return name; }
    public Long getDepartmentHeadId() { return departmentHeadId; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setFacultyId(Long facultyId) { this.facultyId = facultyId; }
    public void setName(String name) { this.name = name; }
    public void setDepartmentHeadId(Long departmentHeadId) { this.departmentHeadId = departmentHeadId; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}