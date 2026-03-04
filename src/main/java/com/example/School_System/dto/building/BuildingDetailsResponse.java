package com.example.School_System.dto.building;

public class BuildingDetailsResponse {
    private String buildingName;
    private String buildingCode;
    private String facultyName;

    public BuildingDetailsResponse(String buildingName, String buildingCode, String facultyName) {
        this.buildingName = buildingName;
        this.buildingCode = buildingCode;
        this.facultyName = facultyName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public String getFacultyName() {
        return facultyName;
    }
}
