package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.AcademicTitle;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_teacher_user"))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_teachers_schools")
    )
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",
            foreignKey = @ForeignKey(name = "fk_teachers_department"))
    private Department department;

    @Column(name = "employee_id", length = 50, unique = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_title", nullable = false)
    private AcademicTitle academicTitle;

    private String qualification;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @OneToMany(mappedBy = "teacherId",fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects = new HashSet<>();

    @OneToOne(mappedBy = "departmentHead", fetch = FetchType.LAZY)
    private Department headOfDepartment;

    public Teacher() {
    }

    public Teacher(User user, School school, Department department, String employeeId, AcademicTitle academicTitle, String qualification, Boolean isActive) {
        this.user = user;
        this.school = school;
        this.department = department;
        this.employeeId = employeeId;
        this.academicTitle = academicTitle;
        this.qualification = qualification;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public School getSchool() { return school; }
    public Department getDepartment() { return department; }
    public String getEmployeeId() { return employeeId; }
    public AcademicTitle getAcademicTitle() { return academicTitle; }
    public String getQualification() { return qualification; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<TeacherSubject> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(Set<TeacherSubject> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    public Department getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(Department headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public void setUser(User user) { this.user = user; }
    public void setSchool(School school) { this.school = school; }
    public void setDepartment(Department department) { this.department = department; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setAcademicTitle(AcademicTitle academicTitle) { this.academicTitle = academicTitle; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}