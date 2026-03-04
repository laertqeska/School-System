package com.example.School_System.dto.classroom;

import java.util.List;

public class PaginatedClassroomsForBuildingResponse {
    private List<ClassroomForBuildingModel> classrooms;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedClassroomsForBuildingResponse() {
    }

    public PaginatedClassroomsForBuildingResponse(List<ClassroomForBuildingModel> classrooms, int page, int perPage, long totalItems, int totalPages) {
        this.classrooms = classrooms;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<ClassroomForBuildingModel> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassroomForBuildingModel> classrooms) {
        this.classrooms = classrooms;
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
