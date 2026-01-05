package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.DegreeLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "study_programs")
public class StudyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_study_programs_department")
    )
    private Department department;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree_level", nullable = false)
    private DegreeLevel degreeLevel;

    @Column(name = "duration_semesters", nullable = false)
    private Integer durationSemesters;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by",nullable = false,
        foreignKey = @ForeignKey(name="fk_study_program_created_by")
    )
    private User createdBy;

    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
    private Set<StudyProgramSubject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "studyProgram", fetch = FetchType.LAZY)
    private Set<SchoolClass> classes = new HashSet<>();

    public StudyProgram() {
    }

    public StudyProgram(Department department, String name, DegreeLevel degreeLevel,
                        Integer durationSemesters, Integer totalCredits) {
        this.department = department;
        this.name = name;
        this.degreeLevel = degreeLevel;
        this.durationSemesters = durationSemesters;
        this.totalCredits = totalCredits;
    }

    public Long getId() { return id; }
    public Department getDepartment() { return department; }
    public String getName() { return name; }
    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public Integer getDurationSemesters() { return durationSemesters; }
    public Integer getTotalCredits() { return totalCredits; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<StudyProgramSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<StudyProgramSubject> subjects) {
        this.subjects = subjects;
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SchoolClass> classes) {
        this.classes = classes;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setDepartment(Department department) { this.department = department; }
    public void setName(String name) { this.name = name; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; }
    public void setDurationSemesters(Integer durationSemesters) { this.durationSemesters = durationSemesters; }
    public void setTotalCredits(Integer totalCredits) { this.totalCredits = totalCredits; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}