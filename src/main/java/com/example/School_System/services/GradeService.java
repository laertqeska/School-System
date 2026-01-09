package com.example.School_System.services;


import com.example.School_System.dto.grade.*;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final AcademicYearRepository academicYearRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final UserRepository userRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, TeacherSubjectRepository teacherSubjectRepository, AcademicYearRepository academicYearRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, UserRepository userRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.teacherRepository = teacherRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.academicYearRepository = academicYearRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.userRepository = userRepository;
    }

    public PaginatedTeachersGradeResponse getGradesForTeacher(Long studyProgramSubjectId, Long classId,int page, int perPage, String search, BigDecimal score){
        if(page > 0){
            page--;
        }
        Pageable pageable = PageRequest.of(page,perPage);
        Page<Grade> gradesPage = gradeRepository.getGradesForTeacher(search,studyProgramSubjectId,classId,score,pageable);
        List<TeachersGradeModel> response = getTeachersGradeModels(gradesPage);
        return new PaginatedTeachersGradeResponse(
                response,
                page + 1,
                perPage,
                gradesPage.getTotalElements(),
                gradesPage.getTotalPages()
        );

    }

    private static List<TeachersGradeModel> getTeachersGradeModels(Page<Grade> gradesPage) {
        List<TeachersGradeModel> response = new ArrayList<>();
        for(Grade grade : gradesPage.getContent()){
            TeachersGradeModel teachersGradeModel = new TeachersGradeModel(
              grade.getStudent().getUser().getFirstName(),
              grade.getStudent().getUser().getLastName(),
                    grade.getSchoolClass().getName(),
                    grade.getScore(),
                    grade.getMaxScore()
            );
            response.add(teachersGradeModel);
        }
        return response;
    }

    public Long createGrade(CreateGradeRequest request,User user){
        StudyProgramSubject studyProgramSubject = studyProgramSubjectRepository.findById(request.getStudyProgramSubjectId()).orElseThrow(() -> new EntityNotFoundException("StudyProgramSubject not found with ID: " + request.getStudyProgramSubjectId()));
        SchoolClass schoolClass = schoolClassRepository.findById(request.getClassId()).orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + request.getClassId()));
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + request.getStudentId()));
        Teacher teacher = teacherRepository.findByUserId(user.getId()).orElseThrow(()->new EntityNotFoundException("Teacher does not exist with user ID: " + user.getId()));
        AcademicYear currentAcademicYear = academicYearRepository.findBySchoolIdAndIsCurrentTrue(student.getSchool().getId())
                .orElseThrow(()-> new EntityNotFoundException("No academic year with this id and is current true"));

        validateGradeAssignment(currentAcademicYear,studyProgramSubject,schoolClass,teacher,student);

        Grade grade = new Grade(
                student,
                studyProgramSubject,
                teacher,
                schoolClass,
                currentAcademicYear,
                request.getGradeType(),
                request.getScore(),
                request.getMaxScore(),
                request.getGradeDate(),
                request.getNotes()
        );
        gradeRepository.save(grade);
        return grade.getId();
    }

    public void updateGrade(Long gradeId,BigDecimal score,User loggedUser){
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(()-> new EntityNotFoundException("Grade not found with ID: " + gradeId));
        if(!grade.getTeacher().getUser().getId().equals(loggedUser.getId())){
            throw new AccessDeniedException("This grade was not assigned by you!");
        }
        grade.setScore(score);
        gradeRepository.save(grade);
    }

    public PaginatedStudentsGradeResponse getGradesForStudent(User user, Integer semester, Integer year, int page, int perPage){
        if(page > 0){
            page--;
        }
        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("Student not found for userId: " + user.getId()));
        Pageable pageable = PageRequest.of(page,perPage);
        Page<StudentGradeModel> gradesForStudent = gradeRepository.getGradesForStudent(student.getId(),pageable,semester,year);

        return new PaginatedStudentsGradeResponse(
                gradesForStudent.getContent(),
                page + 1,
                perPage,
                gradesForStudent.getTotalElements(),
                gradesForStudent.getTotalPages()
        );
    }

    void validateGradeAssignment(
            AcademicYear academicYear,
            StudyProgramSubject studyProgramSubject,
            SchoolClass schoolClass,
            Teacher teacher,
            Student student
    ) {
        boolean isAssigned = teacherSubjectRepository.existsByTeacherAndStudyProgramSubjectAndSchoolClassAndAcademicYear(
                teacher,
                studyProgramSubject,
                schoolClass,
                academicYear
        );
        if(!isAssigned){
            throw new AccessDeniedException(
                    "You are not assigned to teach this subject for this class in the current academic year"
            );
        }

        if(!student.getSchoolClass().getId().equals(schoolClass.getId())){
            throw new AccessDeniedException("You are not assigned to teach this student!");
        }
    }

}
