package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "academic_years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_academic_years_school")
    )
    private School school;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "academicYear", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "academicYear", fetch = FetchType.LAZY)
    private Set<SchoolClass> classes = new HashSet<>();

    @OneToMany(mappedBy = "academicYearId", fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects = new HashSet<>();

    public AcademicYear(){}

    public AcademicYear(School school,Date startDate,Date endDate) {
        this.school = school;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Long getId() { return id; }
    public School getSchool() { return school; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public Boolean getIsCurrent() { return isCurrent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setSchool(School school) { this.school = school; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setIsCurrent(Boolean isCurrent) { this.isCurrent = isCurrent; }
}