package com.example.School_System.dto.building;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateBuildingRequest {
    @NotNull(message = "Faculty id is required")
    private Long facultyId;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100, message = "Building name must be between 3 and 100 characters")
    private String buildingName;
    @NotBlank(message = "Building Code is required")
    @Size(max = 20,message = "Building code must not exceed 20 characters")
    private String buildingCode;

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }
}
