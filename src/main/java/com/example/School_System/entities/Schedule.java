package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.SessionType;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Filter(name = "deletedFilter",condition = "is_deleted = :isDeleted")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type",nullable = false)
    private SessionType sessionType;

    @Column(name="is_active")
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_schedule_classroom")
    )
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_schedule_season")
    )
    private Season season;

    @OneToMany(mappedBy = "schedule",fetch = FetchType.LAZY)
    private Set<ClassSession> classSessions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public Schedule() {
    }

    public Schedule(TeacherSubject teacherSubject, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Classroom classroom,SessionType sessionType,Season season) {
        this.teacherSubject = teacherSubject;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.sessionType = sessionType;
        this.season = season;
    }

    public Long getId() {
        return id;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

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
