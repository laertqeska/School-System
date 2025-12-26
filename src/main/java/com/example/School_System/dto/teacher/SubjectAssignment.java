package com.example.School_System.dto.teacher;

import jakarta.validation.constraints.NotNull;

public class SubjectAssignment {
    @NotNull(message = "Study program subject ID is required")
    private Long studyProgramSubjectId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    public SubjectAssignment() {
    }

    public SubjectAssignment(Long studyProgramSubjectId, Long classId) {
        this.studyProgramSubjectId = studyProgramSubjectId;
        this.classId = classId;
    }

    public Long getStudyProgramSubjectId() {
        return studyProgramSubjectId;
    }

    public void setStudyProgramSubjectId(Long studyProgramSubjectId) {
        this.studyProgramSubjectId = studyProgramSubjectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
