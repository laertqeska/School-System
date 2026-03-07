package com.example.School_System.dto.attendance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TakeAttendanceRequest {
    @NotNull
    private Long scheduleId;
    @NotNull
    private Long schoolClassId;
    @NotNull
    private List<StudentIdAttendanceStatusModel> studentAttendance;
    @NotBlank
    private String topic;
    private Long classroomId;
    private String notes;
    private LocalTime startTime;
    private LocalTime endTime;
    @NotNull
    private LocalDate sessionDate;

    public TakeAttendanceRequest() {
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public List<StudentIdAttendanceStatusModel> getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(List<StudentIdAttendanceStatusModel> studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }
}
