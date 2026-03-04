package com.example.School_System.dto.classroom;

import com.example.School_System.entities.valueObjects.ClassroomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateClassroomRequest {
    @NotNull
    private Long buildingId;

    @NotBlank
    private String classroomNumber;

    @NotBlank
    private String classroomName;

    @NotNull
    private ClassroomType classroomType;

    @NotNull
    @Positive
    private Integer capacity;

    private Boolean hasProjector;

    private Boolean hasAudioSystem;

    private Boolean isAccessible;


    public CreateClassroomRequest() {
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public ClassroomType getClassroomType() {
        return classroomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Boolean getHasProjector() {
        return hasProjector;
    }

    public Boolean getHasAudioSystem() {
        return hasAudioSystem;
    }

    public Boolean getAccessible() {
        return isAccessible;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public void setClassroomType(ClassroomType classroomType) {
        this.classroomType = classroomType;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setHasProjector(Boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public void setHasAudioSystem(Boolean hasAudioSystem) {
        this.hasAudioSystem = hasAudioSystem;
    }

    public void setAccessible(Boolean accessible) {
        isAccessible = accessible;
    }
}
