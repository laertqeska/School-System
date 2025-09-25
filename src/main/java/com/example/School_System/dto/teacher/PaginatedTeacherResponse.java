package com.example.School_System.dto.teacher;

import java.util.List;

public class PaginatedTeacherResponse {
    private List<TeacherModel> teachers;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedTeacherResponse(List<TeacherModel> teachers, int page, int perPage, long totalItems, int totalPages) {
        this.teachers = teachers;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<TeacherModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherModel> teachers) {
        this.teachers = teachers;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
