package com.example.School_System.dto.attendance;

public record StudentAttendanceModel(
        Long studentId,
        String firstName,
        String lastName,
        long numberOfAbsences
)
{}
