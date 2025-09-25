package com.example.School_System.dto.teacher;

import java.util.List;

public class TeacherDetailsResponse {
    private String firstName;
    private String lastName;
    private String schoolName;
    private String departmentName;
    private String email;
    private String employeeId;
    private String academicTitle;
    private String qualification;
    private List<String> subjectsName;
    private List<String> classes;

    public TeacherDetailsResponse(String firstName, String lastName, String schoolName, String departmentName, String email, String employeeId, String academicTitle, String qualification, List<String> subjectsName, List<String> classes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolName = schoolName;
        this.departmentName = departmentName;
        this.email = email;
        this.employeeId = employeeId;
        this.academicTitle = academicTitle;
        this.qualification = qualification;
        this.subjectsName = subjectsName;
        this.classes = classes;
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<String> getSubjectsName() {
        return subjectsName;
    }

    public void setSubjectsName(List<String> subjectsName) {
        this.subjectsName = subjectsName;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
}
