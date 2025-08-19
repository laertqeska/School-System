package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.AcademicTitle;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id", length = 50, unique = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_title", nullable = false)
    private AcademicTitle academicTitle;

    private String qualification;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Teacher(Long userId, Long schoolId, Long departmentId, String employeeId, AcademicTitle academicTitle, String qualification, Boolean isActive) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.departmentId = departmentId;
        this.employeeId = employeeId;
        this.academicTitle = academicTitle;
        this.qualification = qualification;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getSchoolId() { return schoolId; }
    public Long getDepartmentId() { return departmentId; }
    public String getEmployeeId() { return employeeId; }
    public AcademicTitle getAcademicTitle() { return academicTitle; }
    public String getQualification() { return qualification; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setAcademicTitle(AcademicTitle academicTitle) { this.academicTitle = academicTitle; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}