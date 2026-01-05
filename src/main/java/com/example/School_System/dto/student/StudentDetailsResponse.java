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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
}
