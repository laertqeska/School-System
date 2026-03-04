package com.example.School_System.dto.season;

import com.example.School_System.entities.valueObjects.SeasonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateSeasonRequest(
    @NotBlank
    String name,
    @NotBlank
    String code,
    @NotNull
    SeasonType seasonType,
    @NotNull
    LocalDate startDate,
    @NotNull
    LocalDate endDate,
    @NotNull
    Long facultyId,
    @NotNull
    Long academicYearId
){}
