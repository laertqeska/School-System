package com.example.School_System.dto.building;

public class BuildingModel {
    private String buildingName;
    private String buildingCode;

    public BuildingModel(String buildingName, String buildingCode) {
        this.buildingName = buildingName;
        this.buildingCode = buildingCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }
}
