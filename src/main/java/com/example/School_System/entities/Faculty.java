package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_faculties_school"))
    private School school;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dean_id",
            foreignKey = @ForeignKey(name = "fk_faculty_dean"))
    private User dean;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "faculty",fetch = FetchType.LAZY)
    private Set<Department> departments = new HashSet<>();



    public Faculty() {
    }

    public Faculty(School school, String name) {
        this.school = school;
        this.name = name;
    }


    public Long getId() { return id; }
    public School getSchool() { return school; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public User getDean() { return dean; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


    public void setSchoolId(School school) { this.school = school; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDean(User dean) { this.dean = dean; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}