package com.example.School_System.dto.schoolClass;

public class TeacherClassesModel {
    private Long classId;
    private String className;

    public TeacherClassesModel(Long classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public Long getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }
}
