package com.example.School_System.dto.studyProgram;

import java.util.List;

public class PaginatedStudyProgramResponse {
    private List<StudyProgramModel> studyPrograms;
    private int page;
    private int perPage;
    private long totalItems;
    private int totalPages;

    public PaginatedStudyProgramResponse(List<StudyProgramModel> studyPrograms, int page, int perPage, long totalItems, int totalPages) {
        this.studyPrograms = studyPrograms;
        this.page = page;
        this.perPage = perPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<StudyProgramModel> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<StudyProgramModel> studyPrograms) {
        this.studyPrograms = studyPrograms;
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
