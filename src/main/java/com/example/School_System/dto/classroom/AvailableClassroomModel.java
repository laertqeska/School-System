package com.example.School_System.dto.classroom;

import com.example.School_System.entities.valueObjects.ClassroomType;

public class AvailableClassroomModel {
    private Long classRoomId;
    private String classroomNumber;
    private String classroomName;
    private ClassroomType classroomType;
    private Integer capacity;
    private Boolean hasProjector;
    private Boolean isAccessible;

    public AvailableClassroomModel(Long classRoomId, String classroomNumber, String classroomName, ClassroomType classroomType, Integer capacity, Boolean hasProjector, Boolean isAccessible) {
        this.classRoomId = classRoomId;
        this.classroomNumber = classroomNumber;
        this.classroomName = classroomName;
        this.classroomType = classroomType;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.isAccessible = isAccessible;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
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

    public Boolean getAccessible() {
        return isAccessible;
    }

    public void setAccessible(Boolean accessible) {
        isAccessible = accessible;
    }
}
