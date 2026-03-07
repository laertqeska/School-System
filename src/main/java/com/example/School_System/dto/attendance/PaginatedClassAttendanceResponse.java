package com.example.School_System.dto.attendance;

import java.util.List;

public record PaginatedClassAttendanceResponse(
        List<StudentAttendanceModel> studentAttendanceList,
        int page,
        int perPage,
        long totalElements,
        int totalPages
)
{}
