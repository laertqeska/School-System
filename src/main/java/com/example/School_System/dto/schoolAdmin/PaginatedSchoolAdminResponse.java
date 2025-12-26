package com.example.School_System.dto.schoolAdmin;

import java.util.List;

public class PaginatedSchoolAdminResponse {
    private List<SchoolAdminModel> schoolAdmins;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedSchoolAdminResponse(List<SchoolAdminModel> schoolAdmins,int page, int perPage,long totalItems,int totalPages) {
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.perPage = perPage;
        this.page = page;
        this.schoolAdmins = schoolAdmins;
    }

    public List<SchoolAdminModel> getSchoolAdmins() {
        return schoolAdmins;
    }

    public void setSchoolAdmins(List<SchoolAdminModel> schoolAdmins) {
        this.schoolAdmins = schoolAdmins;
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
