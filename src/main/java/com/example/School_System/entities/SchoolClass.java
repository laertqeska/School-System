package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_program_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_classes_study_program"))
    private StudyProgram studyProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_classes_academic_year"))
    private AcademicYear academicYear;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "year_level", nullable = false)
    private Integer yearLevel;

    @Column(name = "max_students")
    private Integer maxStudents = 30;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "classId", fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects = new HashSet<>();

    public SchoolClass() {
    }

    public SchoolClass(StudyProgram studyProgram, AcademicYear academicYear, String name, Integer yearLevel, Integer maxStudents) {
        this.studyProgram = studyProgram;
        this.academicYear = academicYear;
        this.name = name;
        this.yearLevel = yearLevel;
        this.maxStudents = maxStudents;

    }

    public Long getId() { return id; }
    public StudyProgram getStudyProgram() { return studyProgram; }
    public AcademicYear getAcademicYear() { return academicYear; }
    public String getName() { return name; }
    public Integer getYearLevel() { return yearLevel; }
    public Integer getMaxStudents() { return maxStudents; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudyProgram(StudyProgram studyProgram) { this.studyProgram = studyProgram; }
    public void setAcademicYear(AcademicYear academicYear) { this.academicYear = academicYear; }
    public void setName(String name) { this.name = name; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
}