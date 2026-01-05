package com.example.School_System.dto.studyProgramSubject;

public class CreateStudyProgramSubjectRequest {
    private Long subjectId;
    private Integer credits;
    private Integer semester;
    private Integer yearLevel;
//    private String prerequisites;
    private boolean isActive;

    public CreateStudyProgramSubjectRequest() {
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(Integer yearLevel) {
        this.yearLevel = yearLevel;
    }

//    public String getPrerequisites() {
//        return prerequisites;
//    }
//
//    public void setPrerequisites(String prerequisites) {
//        this.prerequisites = prerequisites;
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
