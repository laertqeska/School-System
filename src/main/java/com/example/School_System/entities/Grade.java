package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.AcademicTitle;
import com.example.School_System.entities.valueObjects.GradeType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_grades_student")
    )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_grades_subject")
    )
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_grades_teacher")
    )
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_grades_class")
    )
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_academic_year_grades")
    )
    private AcademicYear academicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_type", nullable = false, length = 20)
    private GradeType gradeType;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "max_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxScore;

    @Column(name = "grade_date", nullable = false)
    private Date gradeDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Grade(Student student, Subject subject, Teacher teacher, SchoolClass schoolClass, AcademicYear academicYear, GradeType gradeType, BigDecimal score, BigDecimal maxScore, Date gradeDate, String notes) {
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.schoolClass = schoolClass;
        this.academicYear = academicYear;
        this.gradeType = gradeType;
        this.score = score;
        this.maxScore = maxScore;
        this.gradeDate = gradeDate;
        this.notes = notes;
    }

    public Grade() {
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Subject getSubject() { return subject; }
    public Teacher getTeacher() { return teacher; }
    public SchoolClass getSchoolClass() { return schoolClass; }
    public AcademicYear getAcademicYear() { return academicYear; }
    public GradeType getGradeType() { return gradeType; }
    public BigDecimal getScore() { return score; }
    public BigDecimal getMaxScore() { return maxScore; }
    public Date getGradeDate() { return gradeDate; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setStudent(Student student) { this.student = student; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public void setSchoolClass(SchoolClass schoolClass) { this.schoolClass = schoolClass; }
    public void setAcademicYear(AcademicYear academicYear) { this.academicYear = academicYear; }
    public void setGradeType(GradeType gradeType) { this.gradeType = gradeType; }
    public void setScore(BigDecimal score) { this.score = score; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    public void setGradeDate(Date gradeDate) { this.gradeDate = gradeDate; }
    public void setNotes(String notes) { this.notes = notes; }
}