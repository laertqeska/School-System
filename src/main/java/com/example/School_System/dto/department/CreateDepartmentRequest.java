package com.example.School_System.dto.department;

import jakarta.validation.constraints.NotBlank;

public class CreateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    private String name;

    public CreateDepartmentRequest() {}

    public CreateDepartmentRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
