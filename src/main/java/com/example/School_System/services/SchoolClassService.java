package com.example.School_System.services;

import com.example.School_System.dto.schoolClass.SchoolClassCreationRequest;
import com.example.School_System.dto.schoolClass.TeacherClassesModel;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserContextService userContextService;

    public SchoolClassService(SchoolClassRepository schoolClassRepository, TeacherRepository teacherRepository, StudyProgramRepository studyProgramRepository, AcademicYearRepository academicYearRepository, SchoolAdminRepository schoolAdminRepository, UserContextService userContextService) {
        this.schoolClassRepository = schoolClassRepository;
        this.teacherRepository = teacherRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.academicYearRepository = academicYearRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.userContextService = userContextService;
    }

    public List<TeacherClassesModel> getClassesForTeacher(Authentication auth){
        User loggedUser = (User) auth.getPrincipal();
        Teacher teacher = teacherRepository.findByUserId(loggedUser.getId()).orElseThrow(() -> new EntityNotFoundException("Teacher not found with user id: " + loggedUser.getId()));
        return schoolClassRepository.getClassesForTeacher(teacher.getId());
    }

    public Long createClass(SchoolClassCreationRequest request,User dean){
        if(!dean.isDean()){
            throw new AccessDeniedException("Only deans can create classes!");
        }
        School school = userContextService.resolveSchool(dean);
        Long schoolId = school.getId();
        StudyProgram studyProgram =  studyProgramRepository.findById(request.getStudyProgramId()).orElseThrow(()-> new EntityNotFoundException("StudyProgram not found with ID: " + request.getStudyProgramId()));
        AcademicYear academicYear = academicYearRepository.findBySchoolIdAndIsCurrentTrue(schoolId).orElseThrow(() -> new EntityNotFoundException("Academic year not found!"));

        SchoolClass schoolClass = new SchoolClass(
                studyProgram,
                academicYear,
                request.getName(),
                request.getYearLevel(),
                request.getMaxStudents()
        );
        schoolClassRepository.save(schoolClass);
        return schoolClass.getId();
    }

}
