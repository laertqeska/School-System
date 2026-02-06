package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Filter(name = "deletedFilter",condition = "is_deleted = isDeleted")
public class Schedule extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_subject_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_schedule_teacher_subject")
    )
    private TeacherSubject teacherSubject;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week",nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;

    @Column(name="effective_from",nullable = false)
    private LocalDate effectiveFrom;

    @Column(name="effective_until",nullable = false)
    private LocalDate effectiveUntil;

    @Column(name="is_active")
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_schedule_classroom")
    )
    private Classroom classroom;

    @OneToMany(mappedBy = "schedule",fetch = FetchType.LAZY)
    private Set<ClassSession> classSessions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public TeacherSubject getTeacherSubject() {
        return teacherSubject;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveUntil() {
        return effectiveUntil;
    }

    public boolean isActive() {
        return isActive;
    }

    public Set<ClassSession> getClassSessions() {
        return classSessions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
