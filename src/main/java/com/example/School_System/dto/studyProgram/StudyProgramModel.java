package com.example.School_System.dto.studyProgram;

public class StudyProgramModel {
    private Long id;
    private String name;
    private String degreeLevel;
    private boolean isActive;

    public StudyProgramModel(Long id, String name, String degreeLevel, boolean isActive) {
        this.id = id;
        this.name = name;
        this.degreeLevel = degreeLevel;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
