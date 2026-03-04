package com.example.School_System.dto.schedule;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class ScheduleForClassResponse {
    private Map<DayOfWeek, List<SchedulePerDayModel>> scheduleMap;

    public ScheduleForClassResponse(Map<DayOfWeek, List<SchedulePerDayModel>> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }

    public Map<DayOfWeek, List<SchedulePerDayModel>> getScheduleMap() {
        return scheduleMap;
    }

    public void setScheduleMap(Map<DayOfWeek, List<SchedulePerDayModel>> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }
}
