package com.example.School_System.dto.season;

import java.util.List;

public record PaginatedSeasonResponse(
        List<SeasonDetailsResponse> seasonsList,
        int page,
        int perPage,
        long totalElements,
        int totalPages
) {
}
