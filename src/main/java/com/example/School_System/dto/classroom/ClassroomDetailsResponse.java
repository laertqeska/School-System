package com.example.School_System.dto.classroom;

import com.example.School_System.entities.valueObjects.ClassroomType;


public class ClassroomDetailsResponse {
    private String buildingName;
    private String classroomNumber;
    private String classroomName;
    private ClassroomType classroomType;
    private Integer capacity;
    private Boolean hasProjector;
    private Boolean hasAudioSystem;
    private Boolean isAccessible;

    public ClassroomDetailsResponse(String buildingName, String classroomNumber, String classroomName, ClassroomType classroomType, Integer capacity, Boolean hasProjector, Boolean hasAudioSystem, Boolean isAccessible) {
        this.buildingName = buildingName;
        this.classroomNumber = classroomNumber;
        this.classroomName = classroomName;
        this.classroomType = classroomType;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.hasAudioSystem = hasAudioSystem;
        this.isAccessible = isAccessible;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public ClassroomType getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(ClassroomType classroomType) {
        this.classroomType = classroomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public Boolean getHasAudioSystem() {
        return hasAudioSystem;
    }

    public void setHasAudioSystem(Boolean hasAudioSystem) {
        this.hasAudioSystem = hasAudioSystem;
    }

    public Boolean getAccessible() {
        return isAccessible;
    }

    public void setAccessible(Boolean accessible) {
        isAccessible = accessible;
    }
}
