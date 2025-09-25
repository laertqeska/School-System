package com.example.School_System.dto.student;

import java.util.Date;

public class StudentDetailsResponse {
    private String firstName;
    private String secondName;
    private String school;
    private int age;
    private String gender;
    private String studentId;
    private String studyProgram;
    private Date dateOfBirth;
    private String personalNumber;
    private String status;
    private Date enrollmentDate;
    private int currentYear;
    private int currentSemester;

    public StudentDetailsResponse(String firstName, String secondName, String school, int age, String gender, String studentId, String studyProgram, Date dateOfBirth, String personalNumber, String status, Date enrollmentDate, int currentYear, int currentSemester) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.school = school;
        this.age = age;
        this.gender = gender;
        this.studentId = studentId;
        this.studyProgram = studyProgram;
        this.dateOfBirth = dateOfBirth;
        this.personalNumber = personalNumber;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
        this.currentYear = currentYear;
        this.currentSemester = currentSemester;
    }
}
