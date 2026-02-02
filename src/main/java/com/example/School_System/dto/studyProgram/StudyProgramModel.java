package com.example.School_System.dto.studyProgram;

import com.example.School_System.entities.valueObjects.DegreeLevel;

public class StudyProgramModel {
    private Long id;
    private String name;
    private DegreeLevel degreeLevel;
    private Boolean isActive;

    public StudyProgramModel(Long id, String name, DegreeLevel degreeLevel, Boolean isActive) {
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

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
