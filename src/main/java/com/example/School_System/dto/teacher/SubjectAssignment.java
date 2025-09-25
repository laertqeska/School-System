package com.example.School_System.dto.teacher;

import jakarta.validation.constraints.NotNull;

public class SubjectAssignment {
    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Class ID is required")
    private Long classId;

    public SubjectAssignment() {
    }

    public SubjectAssignment(Long subjectId, Long classId) {
        this.subjectId = subjectId;
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
