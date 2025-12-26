package com.example.School_System.services;


import com.example.School_System.dto.grade.*;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final AcademicYearRepository academicYearRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final UserRepository userRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, SchoolClassRepository schoolClassRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, AcademicYearRepository academicYearRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, UserRepository userRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.teacherRepository = teacherRepository;
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
        if(student.getSchoolClass().getId() != schoolClass.getId()){
            throw new RuntimeException("Student with ID: " + student.getId() + " does not belong to class with ID: " + schoolClass.getId());
        }
        Teacher teacher = teacherRepository.findByUserId(user.getId()).orElseThrow(()->new EntityNotFoundException("Teacher does not exist with user ID: " + user.getId()));
        AcademicYear currentAcademicYear = academicYearRepository.findBySchoolIdAndIsCurrentTrue(student.getSchool().getId())
                .orElseThrow(()-> new EntityNotFoundException("No academic year with this id and is current true"));
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

    public void updateGrade(Long gradeId,BigDecimal score){
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(()-> new EntityNotFoundException("Grade not found with ID: " + gradeId));
        grade.setScore(score);
        gradeRepository.save(grade);
    }

    public PaginatedStudentsGradeResponse getGradesForStudent(String username, Integer semester, Integer year, int page, int perPage){
        if(page > 0){
            page--;
        }
        User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("User not found with username: " + username));
        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("Student not found for userId: " + user.getId()));
        Pageable pageable = PageRequest.of(page,perPage);
        Page<Grade> gradesForStudent = gradeRepository.getGradesForStudent(student.getId(),pageable);
        List<StudentGradeModel> response = new ArrayList<>();

        for(Grade grade : gradesForStudent.getContent()){
            if(semester != null && !Objects.equals(grade.getStudyProgramSubject().getSemester(), semester)){
                continue;
            }
            if(year != null && !Objects.equals(grade.getStudyProgramSubject().getYearLevel(),year)){
                continue;
            }
            int credits = grade.getStudyProgramSubject().getCredits();
            StudentGradeModel studentGradeModel = new StudentGradeModel(
                    grade.getStudyProgramSubject().getSubject().getName(),
                    credits,
                    grade.getScore(),
                    grade.getMaxScore()
            );
            response.add(studentGradeModel);
        }

        return new PaginatedStudentsGradeResponse(
                response,
                page + 1,
                perPage,
                gradesForStudent.getTotalElements(),
                gradesForStudent.getTotalPages()
        );
    }
}
