package com.example.School_System.dto.grade;

import java.util.List;

public class PaginatedStudentsGradeResponse {
    private List<StudentGradeModel> grades;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedStudentsGradeResponse() {
    }

    public PaginatedStudentsGradeResponse(List<StudentGradeModel> grades, int page, int perPage, long totalItems, int totalPages) {
        this.grades = grades;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<StudentGradeModel> getGrades() {
        return grades;
    }

    public void setGrades(List<StudentGradeModel> grades) {
        this.grades = grades;
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
