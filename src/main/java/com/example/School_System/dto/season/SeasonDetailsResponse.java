package com.example.School_System.dto.season;

import com.example.School_System.entities.valueObjects.SeasonType;

import java.time.LocalDate;

public record SeasonDetailsResponse(
        Long id,
    String name,
    String code,
    SeasonType seasonType,
    LocalDate startDate,
    LocalDate endDate,
    String academicYear
){}
