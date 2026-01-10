package com.example.School_System.services;

import com.example.School_System.dto.department.CreateDepartmentRequest;
import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.DepartmentRepository;
import com.example.School_System.repositories.StudyProgramRepository;
import com.example.School_System.repositories.StudyProgramSubjectRepository;
import com.example.School_System.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final UserContextService userContextService;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;

    public DepartmentService(DepartmentRepository departmentRepository, TeacherRepository teacherRepository, UserContextService userContextService, StudyProgramRepository studyProgramRepository, StudyProgramSubjectRepository studyProgramSubjectRepository){
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.userContextService = userContextService;
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
    }

    public Long createDepartment(User dean, CreateDepartmentRequest request){
        Faculty faculty = dean.getFacultyOfDean();

        Department department = new Department(
                faculty,request.getName()
        );

        department.setCreatedBy(dean);

        Department savedDepartment = departmentRepository.save(department);

        return savedDepartment.getId();
    }

    public List<DepartmentModelResponse> getDepartments(User user){
        Long facultyId = user.getFacultyOfDean().getId();
        return departmentRepository.findDepartmentModelsByFacultyId(facultyId);
    }

    @Transactional
    public void deleteDepartment(User loggedUser,Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + departmentId));
        if(department.getFaculty().getDeleted()){
            throw new IllegalStateException("Cannot delete department because faculty is deleted!");
        }

        School school = userContextService.resolveSchool(loggedUser);
        if(loggedUser.isSchoolAdmin() && !department.getFaculty().getSchool().getId().equals(school.getId())){
            throw new AccessDeniedException("You do not have permission to delete this department!");
        }
        if(loggedUser.isDean() && !department.getFaculty().getDean().getId().equals(loggedUser.getId())){
            throw new AccessDeniedException("You do not have permission to delete this department!");
        }

        department.delete(loggedUser);
        departmentRepository.save(department);
        List<StudyProgram> departmentStudyPrograms = studyProgramRepository.findByDepartmentId(departmentId);
        List<StudyProgramSubject> departmentSubjects = studyProgramSubjectRepository.findStudyProgramSubjectByDepartment(departmentId);

        for(StudyProgram sp : departmentStudyPrograms){
           sp.delete(loggedUser);
        }
        for(StudyProgramSubject sps : departmentSubjects){
            sps.delete(loggedUser);
        }
    }

}
