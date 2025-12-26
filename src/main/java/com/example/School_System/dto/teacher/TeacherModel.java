package com.example.School_System.dto.teacher;

public class TeacherModel {
    private String firstName;
    private String lastName;
    private String email;
    private String departmentName;
    private String academicTitle;

    public TeacherModel(String firstName,String lastName,String email,String departmentName,String academicTitle) {
        this.academicTitle = academicTitle;
        this.departmentName = departmentName;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
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
}
