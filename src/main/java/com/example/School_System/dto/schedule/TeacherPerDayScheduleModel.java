package com.example.School_System.dto.schedule;

import com.example.School_System.entities.valueObjects.SessionType;

public class TeacherPerDayScheduleModel {
    private String studyProgramName;
    private String subjectName;
    private SessionType sessionType;
    private int yearLevel;
    private String schoolClassName;
    private String classroomNumber;

    public TeacherPerDayScheduleModel(String studyProgramName, String subjectName, SessionType sessionType, int yearLevel, String schoolClassName, String classroomNumber) {
        this.studyProgramName = studyProgramName;
        this.subjectName = subjectName;
        this.sessionType = sessionType;
        this.yearLevel = yearLevel;
        this.schoolClassName = schoolClassName;
        this.classroomNumber = classroomNumber;
    }

    public String getStudyProgramName() {
        return studyProgramName;
    }

    public void setStudyProgramName(String studyProgramName) {
        this.studyProgramName = studyProgramName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }
}
