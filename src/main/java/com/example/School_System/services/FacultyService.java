package com.example.School_System.services;

import com.example.School_System.dto.faculty.CreateFacultyRequest;
import com.example.School_System.dto.faculty.FacultyModel;
import com.example.School_System.dto.faculty.PaginatedFacultyResponse;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.FacultyApprovalTokenRepository;
import com.example.School_System.repositories.FacultyRepository;
import com.example.School_System.repositories.SchoolAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final FacultyApprovalTokenRepository facultyApprovalTokenRepository;
    private final FacultyApprovalService facultyApprovalService;

    public FacultyService(FacultyRepository facultyRepository, SchoolAdminRepository schoolAdminRepository, FacultyApprovalTokenRepository facultyApprovalTokenRepository, FacultyApprovalService facultyApprovalService){
        this.facultyRepository = facultyRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.facultyApprovalTokenRepository = facultyApprovalTokenRepository;
        this.facultyApprovalService = facultyApprovalService;
    }

    @Transactional
    public Long createFaculty(CreateFacultyRequest request, User user){
        Long userId = user.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(userId).orElseThrow(()-> new EntityNotFoundException("School admin not found with user ID: " + userId));
        School school = schoolAdmin.getSchool();
        Faculty faculty = new Faculty(
                school,
                request.getFacultyName(),
                request.getCode(),
                user
        );
        Faculty createdFaculty = facultyRepository.save(faculty);
        facultyApprovalService.createFacultyApproval(createdFaculty);
        return createdFaculty.getId();
    }

    public PaginatedFacultyResponse getAllFaculties(User user,String search,int page, int perPage){
        Long userId = user.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(userId).orElseThrow(()->new EntityNotFoundException("School admin not found with user ID: " + userId));
        School school = schoolAdmin.getSchool();
        Pageable pageable = PageRequest.of(page,perPage);
        Page<FacultyModel> faculties = facultyRepository.getFaculties(pageable,search,school.getId());
        return new PaginatedFacultyResponse(faculties.getContent(),page,perPage,faculties.getTotalElements(),faculties.getTotalPages());
    }
}
