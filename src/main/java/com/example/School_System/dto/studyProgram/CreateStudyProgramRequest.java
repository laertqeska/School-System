package com.example.School_System.dto.studyProgram;

import com.example.School_System.entities.valueObjects.DegreeLevel;

public class CreateStudyProgramRequest {
    private String name;
    private DegreeLevel degreeLevel;
    private boolean isActive;
    private Integer durationSemesters;
    private Integer totalCredits;

    public CreateStudyProgramRequest() {
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

    public Integer getDurationSemesters() {
        return durationSemesters;
    }

    public void setDurationSemesters(Integer durationSemesters) {
        this.durationSemesters = durationSemesters;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }
}
