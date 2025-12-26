package com.example.School_System.dto.schoolClass;

public class SchoolClassCreationRequest {
    private String name;
    private Long studyProgramId;
    private Integer yearLevel;
    private Integer maxStudents;

    public SchoolClassCreationRequest(String name, Long studyProgramId, Integer yearLevel, Integer maxStudents) {
        this.name = name;
        this.studyProgramId = studyProgramId;
        this.yearLevel = yearLevel;
        this.maxStudents = maxStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    public Integer getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(Integer yearLevel) {
        this.yearLevel = yearLevel;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }
}
