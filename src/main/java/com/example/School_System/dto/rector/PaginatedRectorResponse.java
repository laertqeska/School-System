package com.example.School_System.dto.rector;

import java.util.List;

public class PaginatedRectorResponse {
    private List<RectorModel> rectors;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedRectorResponse(List<RectorModel> rectors, int page, int perPage, long totalItems, int totalPages) {
        this.rectors = rectors;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<RectorModel> getRectors() {
        return rectors;
    }

    public void setRectors(List<RectorModel> rectors) {
        this.rectors = rectors;
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
