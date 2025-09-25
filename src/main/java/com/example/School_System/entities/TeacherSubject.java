package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "subject_id", "class_id", "academic_year_id"}))
public class TeacherSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "academic_year_id", nullable = false)
    private Long academicYearId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TeacherSubject() {
    }

    public TeacherSubject(Long teacherId, Long subjectId, Long classId, Long academicYearId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.classId = classId;
        this.academicYearId = academicYearId;
    }

    public Long getId() { return id; }
    public Long getTeacherId() { return teacherId; }
    public Long getSubjectId() { return subjectId; }
    public Long getClassId() { return classId; }
    public Long getAcademicYearId() { return academicYearId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public void setClassId(Long classId) { this.classId = classId; }
    public void setAcademicYearId(Long academicYearId) { this.academicYearId = academicYearId; }
}