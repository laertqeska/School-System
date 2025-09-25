package com.example.School_System.dto.school;

import java.util.List;

public class PaginatedSchoolResponse {
    private List<SchoolModel> schools;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedSchoolResponse(List<SchoolModel> schools, int page, int perPage, long totalItems, int totalPages) {
        this.schools = schools;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<SchoolModel> getSchools() {
        return schools;
    }

    public void setSchools(List<SchoolModel> schools) {
        this.schools = schools;
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
