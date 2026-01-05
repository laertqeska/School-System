package com.example.School_System.dto.subject;

public class CreateSubjectRequest {
    private String name;
    private String code;
    private Long departmentId;
    private String description;
    private boolean isActive;

    public CreateSubjectRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDepartmentId(){ return departmentId;}

    public void setDepartmentId(Long departmentId){ this.departmentId = departmentId;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
