package com.example.School_System.dto.teacher;

import com.example.School_System.entities.valueObjects.AcademicTitle;

public class UpdateTeacherRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String employeeId;
    private AcademicTitle academicTitle;
    private String qualification;

    public UpdateTeacherRequest() {
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public AcademicTitle getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(AcademicTitle academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
