package com.example.School_System.dto.attendance;

import com.example.School_System.entities.Student;
import com.example.School_System.entities.valueObjects.AttendanceStatus;

public class StudentAttendanceStatusModel {
    private Student student;
    private AttendanceStatus attendanceStatus;

    public StudentAttendanceStatusModel(Student student, AttendanceStatus attendanceStatus) {
        this.student = student;
        this.attendanceStatus = attendanceStatus;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
