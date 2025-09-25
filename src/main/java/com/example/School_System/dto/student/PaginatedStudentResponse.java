package com.example.School_System.dto.student;

import java.util.List;

public class PaginatedStudentResponse {
    private List<StudentModel> students;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedStudentResponse(List<StudentModel> students, int page, int perPage, long totalItems, int totalPages) {
        this.students = students;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(List<StudentModel> students) {
        this.students = students;
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
