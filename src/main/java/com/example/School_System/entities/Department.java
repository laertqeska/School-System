package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_departments_faculty")
    )
    private Faculty faculty;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_head_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_department_head")
    )
    private Teacher departmentHead;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    public Department(){}

    public Department(Faculty faculty, String name) {
        this.faculty = faculty;
        this.name = name;
    }

    public Long getId() { return id; }
    public Faculty getFaculty() { return faculty; }
    public String getName() { return name; }
    public Teacher getDepartmentHead() { return departmentHead; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
    public void setName(String name) { this.name = name; }
    public void setDepartmentHead(Teacher departmentHead) { this.departmentHead = departmentHead; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}