package com.example.School_System.dto.teacher;

import com.example.School_System.entities.valueObjects.AcademicTitle;

public class TeacherModel {
    private String firstName;
    private String lastName;
    private String email;
    private String departmentName;
    private AcademicTitle academicTitle;

    public TeacherModel(String firstName,String lastName,String email,String departmentName,AcademicTitle academicTitle) {
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

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }
}
