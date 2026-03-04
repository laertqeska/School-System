package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.SeasonStatus;
import com.example.School_System.entities.valueObjects.SeasonType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seasons")
public class Season extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "season_type",nullable = false)
    private SeasonType seasonType;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "season_status",nullable = false)
    private SeasonStatus status = SeasonStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="academic_year_id",nullable = false,
        foreignKey = @ForeignKey(name ="fk_season_academic_year")
    )
    private AcademicYear academicYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_season_faculty")
    )
    private Faculty faculty;

    public Season(){}

    public Season(String name,String code,SeasonType seasonType, LocalDate startDate,LocalDate endDate,SeasonStatus status,AcademicYear academicYear,Faculty faculty){
        this.name = name;
        this.code = code;
        this.seasonType = seasonType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.academicYear = academicYear;
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public SeasonType getSeasonType() {
        return seasonType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public SeasonStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
