package com.example.School_System.dto.rector;

import java.util.List;

public class PaginatedRectorFacultiesResponse {
    private List<RectorFacultiesModel> faculties;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedRectorFacultiesResponse(List<RectorFacultiesModel> faculties, int page, int perPage, long totalItems, int totalPages) {
        this.faculties = faculties;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<RectorFacultiesModel> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<RectorFacultiesModel> faculties) {
        this.faculties = faculties;
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
