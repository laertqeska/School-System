package com.example.School_System.services;

import com.example.School_System.dto.season.CreateSeasonRequest;
import com.example.School_System.dto.season.PaginatedSeasonResponse;
import com.example.School_System.dto.season.SeasonDetailsResponse;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.SeasonStatus;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;
    private final FacultyRepository facultyRepository;
    private final AcademicYearRepository academicYearRepository;
    private final TeacherRepository teacherRepository;

    public SeasonService(SeasonRepository seasonRepository, FacultyRepository facultyRepository, AcademicYearRepository academicYearRepository, TeacherRepository teacherRepository) {
        this.seasonRepository = seasonRepository;
        this.facultyRepository = facultyRepository;
        this.academicYearRepository = academicYearRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Long createSeason(User loggedUser, CreateSeasonRequest request){
        Long facultyId = request.facultyId();
        Long academicYearId = request.academicYearId();
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(()->new EntityNotFoundException("Faculty not found for the given id: " + facultyId));
        AcademicYear academicYear = academicYearRepository.findById(academicYearId)
                .orElseThrow(()->new EntityNotFoundException("Academic Year not found for id: " + academicYearId));
        if(request.startDate().isAfter(request.endDate()) || request.startDate().equals(request.endDate())){
            throw new IllegalArgumentException("End date must be after start date");
        }
        if(seasonRepository.existsByFaculty_IdAndCodeAndIsDeletedFalse(facultyId,request.code())){
            throw new IllegalArgumentException("Season already exists by faculty and code");
        }
        Season season = new Season(
                request.name(),
                request.code(),
                request.seasonType(),
                request.startDate(),
                request.endDate(),
                SeasonStatus.DRAFT,
                academicYear,
                faculty
        );
        Season savedSeason = seasonRepository.save(season);
        return savedSeason.getId();
    }

    public SeasonDetailsResponse getSeasonDetails(User loggedUser, Long seasonId){
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(()->new EntityNotFoundException("Season not found for id: " + seasonId));
        AcademicYear academicYear = season.getAcademicYear();
        return new SeasonDetailsResponse(
                seasonId,
                season.getName(),
                season.getCode(),
                season.getSeasonType(),
                season.getStartDate(),
                season.getEndDate(),
                academicYear.getStartDate().toString() + academicYear.getEndDate().toString()
        );
    }

    public PaginatedSeasonResponse getSeasonsByFaculty(Long facultyId,SeasonStatus status,int page, int perPage){
        if (page < 1) {
            throw new IllegalArgumentException("Page number cannot be smaller than 1");
        }
        else page--;

        Pageable pageable = PageRequest.of(page,perPage);
        Page<SeasonDetailsResponse> seasonsPage;
        if(status != null){
            seasonsPage = seasonRepository.findByFaculty_IdAndStatusAndIsDeletedFalse(facultyId, status, pageable);
        }
        else{
            seasonsPage = seasonRepository.findByFaculty_IdAndIsDeletedFalse(facultyId, pageable);
        }

        return new PaginatedSeasonResponse(
                seasonsPage.getContent(),
                page + 1,
                perPage,
                seasonsPage.getTotalElements(),
                seasonsPage.getTotalPages()
        );

    }

    public PaginatedSeasonResponse getSeasonsByDepartment(int page, int perPage,User loggedUser){
        if(page < 1){
            throw new IllegalArgumentException("Page cannot be smaller than 1");
        }
        else page--;
        Pageable pageable = PageRequest.of(page,perPage);

        Long userId = loggedUser.getId();
        Teacher departmentHead = teacherRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found with is deleted false and  user id: " + userId));
        Department department = departmentHead.getDepartment();
        Long facultyId = department.getFaculty().getId();
        Page<SeasonDetailsResponse> seasonsPage = seasonRepository.findByFaculty_IdAndIsDeletedFalse(facultyId, pageable);

        return new PaginatedSeasonResponse(
                seasonsPage.getContent(),
                page + 1,
                perPage,
                seasonsPage.getTotalElements(),
                seasonsPage.getTotalPages()
        );
    }



}
