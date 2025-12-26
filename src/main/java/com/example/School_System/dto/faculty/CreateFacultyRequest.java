package com.example.School_System.dto.faculty;

public class CreateFacultyRequest {
    String facultyName;
    String code;

    public CreateFacultyRequest() {
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
