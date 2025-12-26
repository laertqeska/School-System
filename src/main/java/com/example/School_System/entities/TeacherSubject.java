package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_study_program_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "subject_id", "class_id", "academic_year_id"}))
public class TeacherSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_teacher_subject_teacher")
    )
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_teacher_subject_study_program_subject")
    )
    private StudyProgramSubject studyProgramSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_teacher_subject_class")
    )
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_teacher_subject_academic_year")
    )
    private AcademicYear academicYear;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TeacherSubject() {
    }

    public TeacherSubject(Teacher teacher, StudyProgramSubject studyProgramSubject, SchoolClass schoolClass, AcademicYear academicYear) {
        this.teacher = teacher;
        this.studyProgramSubject = studyProgramSubject;
        this.schoolClass = schoolClass;
        this.academicYear = academicYear;
    }

    public Long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public StudyProgramSubject getStudyProgramSubject() {
        return studyProgramSubject;
    }

    public void setStudyProgramSubject(StudyProgramSubject studyProgramSubject) {
        this.studyProgramSubject = studyProgramSubject;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}