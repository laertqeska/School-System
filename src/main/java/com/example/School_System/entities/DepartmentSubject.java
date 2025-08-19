package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "department_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"department_id", "subject_id"}))
public class DepartmentSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "is_primary_department")
    private Boolean isPrimaryDepartment = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public DepartmentSubject(Long departmentId,Long subjectId) {
        this.departmentId = departmentId;
        this.subjectId = subjectId;
    }


    public Long getId() { return id; }
    public Long getDepartmentId() { return departmentId; }
    public Long getSubjectId() { return subjectId; }
    public Boolean getIsPrimaryDepartment() { return isPrimaryDepartment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public void setIsPrimaryDepartment(Boolean isPrimaryDepartment) { this.isPrimaryDepartment = isPrimaryDepartment; }
}
