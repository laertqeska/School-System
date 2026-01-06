package com.example.School_System.dto.teaching;

public class AssignTeacherToClassAndSubjectRequest {
    private Long teacherId;
    private Long schoolClassId;
    private Long studyProgramSubjectId;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Long getStudyProgramSubjectId() {
        return studyProgramSubjectId;
    }

    public void setStudyProgramSubjectId(Long studyProgramSubjectId) {
        this.studyProgramSubjectId = studyProgramSubjectId;
    }
}
