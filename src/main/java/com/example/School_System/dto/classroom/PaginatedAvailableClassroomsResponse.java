package com.example.School_System.dto.classroom;

import java.util.List;

public class PaginatedAvailableClassroomsResponse {
    List<AvailableClassroomModel> availableClassrooms;
    int page;
    int perPage;
    long totalElements;
    int totalPages;

    public PaginatedAvailableClassroomsResponse(List<AvailableClassroomModel> availableClassrooms, int page, int perPage, long totalElements, int totalPages) {
        this.availableClassrooms = availableClassrooms;
        this.page = page;
        this.perPage = perPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<AvailableClassroomModel> getAvailableClassrooms() {
        return availableClassrooms;
    }

    public void setAvailableClassrooms(List<AvailableClassroomModel> availableClassrooms) {
        this.availableClassrooms = availableClassrooms;
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

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
