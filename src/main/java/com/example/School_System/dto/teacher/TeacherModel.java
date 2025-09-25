package com.example.School_System.dto.teacher;

public class TeacherModel {
    private String firstName;
    private String secondName;
    private String email;
    private String departmentName;
    private String academicTitle;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public TeacherModel(String firstName, String secondName, String email, String departmentName, String academicTitle) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.departmentName = departmentName;
        this.academicTitle = academicTitle;
    }
}
