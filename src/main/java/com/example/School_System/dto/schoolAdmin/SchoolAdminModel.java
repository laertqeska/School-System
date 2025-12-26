package com.example.School_System.dto.schoolAdmin;

public class SchoolAdminModel {
    private String firstName;
    private String secondName;
    private String email;
    private String schoolName;

    public SchoolAdminModel(String firstName, String secondName, String email, String schoolName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.schoolName = schoolName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
