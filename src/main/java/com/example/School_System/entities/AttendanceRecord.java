package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.AttendanceStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records",
uniqueConstraints = {
        @UniqueConstraint(name = "UniqueStudentAndSession",
                columnNames = {"student_id","class_session_id"})
    }
)
public class AttendanceRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_attendance_student")
    )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="class_session_id",nullable = false,
        foreignKey = @ForeignKey(name = "fk_attendance_record_class_session")
    )
    private ClassSession classSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private AttendanceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",nullable = false,
        foreignKey = @ForeignKey(name = "fk_attendance_record_user")
    )
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public ClassSession getClassSession() {
        return classSession;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
