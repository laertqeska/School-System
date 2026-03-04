package com.example.School_System.services;

import com.example.School_System.dto.teaching.AssignTeacherToClassAndSubjectRequest;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TeacherAssignmentService {
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final AcademicYearRepository academicYearRepository;
    private SchoolContextService schoolContextService;


    public TeacherAssignmentService(TeacherSubjectRepository teacherSubjectRepository, TeacherRepository teacherRepository, SchoolClassRepository schoolClassRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, AcademicYearRepository academicYearRepository, SchoolContextService schoolContextService) {
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.academicYearRepository = academicYearRepository;
        this.schoolContextService = schoolContextService;
    }

    @Transactional
    public void assignTeacherToSubjectAndClass(AssignTeacherToClassAndSubjectRequest request, User loggedUser){
        School school = schoolContextService.resolveSchool(loggedUser);
        Long teacherId = request.getTeacherId();
        Long schoolClassId = request.getSchoolClassId();
        Long studyProgramSubjectId = request.getStudyProgramSubjectId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()->new EntityNotFoundException("Teacher not found with ID: " + teacherId));
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                .orElseThrow(()-> new EntityNotFoundException("School class not found with ID: " + schoolClassId));
        StudyProgramSubject studyProgramSubject = studyProgramSubjectRepository.findById(studyProgramSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Study program subject not found with ID: " + studyProgramSubjectId));
        validateAssignment(teacher,schoolClass,studyProgramSubject,school);
        AcademicYear  academicYear = academicYearRepository.findBySchoolIdAndIsCurrentTrue(school.getId())
                .orElseThrow(() -> new EntityNotFoundException("Current academic year not found for school id: " + school.getId()));

        boolean existingTeacherSubject = teacherSubjectRepository.existsByTeacherAndStudyProgramSubjectAndSchoolClassAndAcademicYear(teacher,studyProgramSubject,schoolClass,academicYear);
        if(existingTeacherSubject){
            throw new IllegalStateException("Teacher subject already exists!");
        }

        TeacherSubject teacherSubject = new TeacherSubject(
                teacher,
                studyProgramSubject,
                schoolClass,
                academicYear
        );
        teacherSubjectRepository.save(teacherSubject);
    }

    public void validateAssignment(Teacher teacher, SchoolClass schoolClass, StudyProgramSubject studyProgramSubject, School school){
        Long schoolId = school.getId();
        if(!teacher.getSchool().getId().equals(schoolId)){
            throw new IllegalArgumentException("Teacher not found for school id: " + schoolId);
        }
        if(!schoolClass.getStudyProgram().getDepartment().getFaculty().getSchool().getId().equals(schoolId)){
            throw new IllegalArgumentException("School class not found for school id: " + schoolId);
        }
        if(!studyProgramSubject.getStudyProgram().getDepartment().getFaculty().getSchool().getId().equals(schoolId)){
            throw new IllegalArgumentException("Study program subject not found for school id: " + schoolId);
        }
        if(!schoolClass.getStudyProgram().getId().equals(studyProgramSubject.getStudyProgram().getId())){
            throw new IllegalArgumentException("Study program of school class and subject should not differ!");
        }
    }
}
