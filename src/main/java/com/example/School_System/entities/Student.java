package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.StudentStatus;
import com.example.School_System.entities.valueObjects.Gender;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "study_program_id", nullable = false)
    private Long studyProgramId;

    @Column(name = "student_id", length = 50, unique = true)
    private String studentId;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "current_year")
    private Integer currentYear;

    @Column(name = "current_semester")
    private Integer currentSemester;

    @Enumerated(EnumType.STRING)
    private StudentStatus status = StudentStatus.ACTIVE;

    @Column(name = "personal_number", length = 20)
    private String personalNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Student(Long userId, Long schoolId, Long studyProgramId, String studentId, Date enrollmentDate, StudentStatus status, String personalNumber, Date dateOfBirth, Gender gender, String address) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.studyProgramId = studyProgramId;
        this.studentId = studentId;
        this.enrollmentDate = enrollmentDate;
        this.currentYear = 1;
        this.currentSemester = 1;
        this.status = status;
        this.personalNumber = personalNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getSchoolId() { return schoolId; }
    public Long getStudyProgramId() { return studyProgramId; }
    public String getStudentId() { return studentId; }
    public Date getEnrollmentDate() { return enrollmentDate; }
    public Integer getCurrentYear() { return currentYear; }
    public Integer getCurrentSemester() { return currentSemester; }
    public StudentStatus getStatus() { return status; }
    public String getPersonalNumber() { return personalNumber; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public Gender getGender() { return gender; }
    public String getAddress() { return address; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
    public void setStudyProgramId(Long studyProgramId) { this.studyProgramId = studyProgramId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setEnrollmentDate(Date enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public void setCurrentYear(Integer currentYear) { this.currentYear = currentYear; }
    public void setCurrentSemester(Integer currentSemester) { this.currentSemester = currentSemester; }
    public void setStatus(StudentStatus status) { this.status = status; }
    public void setPersonalNumber(String personalNumber) { this.personalNumber = personalNumber; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setAddress(String address) { this.address = address; }
}