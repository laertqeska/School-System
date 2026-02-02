package com.example.School_System.services;

import com.example.School_System.dto.faculty.CreateFacultyRequest;
import com.example.School_System.dto.faculty.FacultyModel;
import com.example.School_System.dto.faculty.PaginatedFacultyResponse;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyApprovalTokenRepository facultyApprovalTokenRepository;
    private final FacultyApprovalService facultyApprovalService;
    private final UserContextService userContextService;

    public FacultyService(FacultyRepository facultyRepository, SchoolAdminRepository schoolAdminRepository, StudyProgramRepository studyProgramRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, DepartmentRepository departmentRepository, FacultyApprovalTokenRepository facultyApprovalTokenRepository, FacultyApprovalService facultyApprovalService, UserContextService userContextService){
        this.facultyRepository = facultyRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.departmentRepository = departmentRepository;
        this.facultyApprovalTokenRepository = facultyApprovalTokenRepository;
        this.facultyApprovalService = facultyApprovalService;
        this.userContextService = userContextService;
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

    @Transactional
    public void deleteFaculty(User loggedUser, Long facultyId){
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + facultyId));
        if(faculty.getDeleted()){
            throw new IllegalStateException("Faculty is already deleted!");
        }
        School adminSchool = userContextService.resolveSchool(loggedUser);
        if(!faculty.getSchool().getId().equals(adminSchool.getId())){
            throw new AccessDeniedException("You do not have permission to delete this faculty!");
        }
        faculty.delete(loggedUser);
        List<Department> departments = departmentRepository.findByFacultyId(faculty.getId());
        List<StudyProgram> studyPrograms = studyProgramRepository.findByDepartmentFacultyId(faculty.getId());
        List<StudyProgramSubject> studyProgramSubjects = studyProgramSubjectRepository.findByStudyProgramDepartmentFacultyId(faculty.getId());
        departments.forEach(d -> d.delete(loggedUser));
        studyPrograms.forEach(sp -> sp.delete(loggedUser));
        studyProgramSubjects.forEach(sps -> sps.delete(loggedUser));

        facultyRepository.save(faculty);
        departmentRepository.saveAll(departments);
        studyProgramRepository.saveAll(studyPrograms);
        studyProgramSubjectRepository.saveAll(studyProgramSubjects);
    }
}
