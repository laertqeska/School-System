package com.example.School_System.dto.attendance;

import com.example.School_System.entities.valueObjects.AttendanceStatus;

public class StudentIdAttendanceModel {
    private Long id;
    private AttendanceStatus status;

    public StudentIdAttendanceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
