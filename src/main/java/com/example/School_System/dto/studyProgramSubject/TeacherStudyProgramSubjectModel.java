package com.example.School_System.dto.studyProgramSubject;

public class TeacherStudyProgramSubjectModel {
    private Long studyProgramSubjectId;
    private String studyProgramSubjectName;

    public TeacherStudyProgramSubjectModel(Long studyProgramSubjectId, String studyProgramSubjectName) {
        this.studyProgramSubjectId = studyProgramSubjectId;
        this.studyProgramSubjectName = studyProgramSubjectName;
    }

    public Long getStudyProgramSubjectId() {
        return studyProgramSubjectId;
    }

    public void setStudyProgramSubjectId(Long studyProgramSubjectId) {
        this.studyProgramSubjectId = studyProgramSubjectId;
    }

    public String getStudyProgramSubjectName() {
        return studyProgramSubjectName;
    }

    public void setStudyProgramSubjectName(String studyProgramSubjectName) {
        this.studyProgramSubjectName = studyProgramSubjectName;
    }
}
