package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.SessionStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "class_sessions")
public class ClassSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_class_session_schedule")
    )
    private Schedule schedule;

    @Column(name = "session_date",nullable = false)
    private LocalDate sessionDate;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;

    @Column(name = "topic",nullable = false)
    private String topic;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private SessionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_class_session_classroom")
    )
    private Classroom classroom;

    @Column(name = "notes")
    private String notes;
}
