package com.example.School_System.dto.student;

public class StudentModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String studentId;
    private String studyProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public StudentModel(Long id, String firstName, String lastName, String studentId, String studyProgram) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.studyProgram = studyProgram;
    }
}
