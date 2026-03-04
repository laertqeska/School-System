package com.example.School_System.dto.schedule;

import com.example.School_System.entities.valueObjects.SessionType;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleForClassModel {
    private String studySubjectName;
    private SessionType type;
    private String teacherFullName;
    private String classroomNumber;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduleForClassModel(String studySubjectName, SessionType type, String teacherFullName, String classroomNumber, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.studySubjectName = studySubjectName;
        this.type = type;
        this.teacherFullName = teacherFullName;
        this.classroomNumber = classroomNumber;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStudySubjectName() {
        return studySubjectName;
    }

    public void setStudySubjectName(String studySubjectName) {
        this.studySubjectName = studySubjectName;
    }

    public SessionType getType() {
        return type;
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public String getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(String classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
