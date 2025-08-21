package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "academic_years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

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


    public AcademicYear(Long schoolId,Date startDate,Date endDate) {
        this.schoolId = schoolId;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Long getId() { return id; }
    public Long getSchoolId() { return schoolId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public Boolean getIsCurrent() { return isCurrent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setIsCurrent(Boolean isCurrent) { this.isCurrent = isCurrent; }
}