package com.example.School_System.services;

import com.example.School_System.dto.subject.CreateSubjectRequest;
import com.example.School_System.dto.subject.SchoolAdminSubjectModel;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.DepartmentRepository;
import com.example.School_System.repositories.SchoolAdminRepository;
import com.example.School_System.repositories.SchoolRepository;
import com.example.School_System.repositories.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserContextService userContextService;
    private final SchoolAdminRepository schoolAdminRepository;
    private final SchoolRepository schoolRepository;
    private final DepartmentRepository departmentRepository;

    public SubjectService(SubjectRepository subjectRepository, UserContextService userContextService, SchoolAdminRepository schoolAdminRepository, SchoolRepository schoolRepository, DepartmentRepository departmentRepository){
        this.subjectRepository = subjectRepository;
        this.userContextService = userContextService;
        this.schoolAdminRepository = schoolAdminRepository;
        this.schoolRepository = schoolRepository;
        this.departmentRepository = departmentRepository;
    }

    public Long createSubject(User dean, CreateSubjectRequest request){
        Long departmentId = request.getDepartmentId();
        if(!dean.isDean()){
            throw new AccessDeniedException("Only deans can create subjects!");
        }
        School school = userContextService.resolveSchool(dean);
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));
        if(!department.getFaculty().getSchool().getId().equals(school.getId())){
            throw new AccessDeniedException("Department does not belong to your school");
        }
        Subject subject = new Subject(
                school,
                request.getName(),
                request.getCode(),
                request.getDescription(),
                department,
                request.isActive()
        );
        subject.setCreatedBy(dean);
        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject.getId();
    }



    public List<SchoolAdminSubjectModel> getSubjectsForDepartment(Long departmentId){
        return subjectRepository.findByDepartment(departmentId);
    }
}
