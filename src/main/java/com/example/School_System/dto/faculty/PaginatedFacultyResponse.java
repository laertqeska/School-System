package com.example.School_System.dto.faculty;

import java.util.List;

public class PaginatedFacultyResponse {
    private List<FacultyModel> faculties;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedFacultyResponse(List<FacultyModel> faculties, int page, int perPage, long totalItems, int totalPages) {
        this.faculties = faculties;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
