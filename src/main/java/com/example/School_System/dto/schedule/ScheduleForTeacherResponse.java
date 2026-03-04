package com.example.School_System.dto.schedule;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ScheduleForTeacherResponse {
    Map<DayOfWeek,List<TeacherPerDayScheduleModel>> schedule;

    public ScheduleForTeacherResponse(Map<DayOfWeek, List<TeacherPerDayScheduleModel>> schedule) {
        this.schedule = schedule;
    }

    public Map<DayOfWeek, List<TeacherPerDayScheduleModel>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<DayOfWeek, List<TeacherPerDayScheduleModel>> schedule) {
        this.schedule = schedule;
    }
}
