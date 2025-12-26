package com.example.School_System.services;

import com.example.School_System.dto.mappers.TeacherMapper;
import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.dto.teacher.TeacherDetailsResponse;
import com.example.School_System.dto.teacher.TeacherModel;
import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, SchoolClassRepository schoolClassRepository, SchoolAdminRepository schoolAdminRepository, StudyProgramSubjectRepository studyProgramSubjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
    }

    public PaginatedTeacherResponse listTeachers(Authentication auth, int page, int perPage, String search){
        User loggedUser = (User) auth.getPrincipal();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(loggedUser.getId())
                .orElseThrow(()-> new EntityNotFoundException("School Admin not found for user id : " + loggedUser.getId()));
        Long schoolId = schoolAdmin.getSchool().getId();
        if(page > 0) page--;
        Pageable pageable = PageRequest.of(page,perPage);
        Page<Teacher> teacherPage = null;
        if(search == null || search.isEmpty()){
            teacherPage =  teacherRepository.findBySchoolId(pageable,schoolId);
        }
        else{
            teacherPage = teacherRepository.findTeachersWithSearch(search,schoolId,pageable);
        }
        List<TeacherModel> response = new ArrayList<>();
        for(Teacher teacher : teacherPage.getContent()){
            User teacherUser = teacher.getUser();
            TeacherModel teacherModel = new TeacherModel(
                    teacherUser.getFirstName(),
                    teacherUser.getLastName(),
                    teacherUser.getEmail(),
                    teacher.getDepartment().getName(),
                    teacher.getAcademicTitle().toString()
            );
            response.add(teacherModel);
        }

        return new PaginatedTeacherResponse(
                response,
                page + 1,
                perPage,
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages()
        );
    }

    public PaginatedTeacherResponse getTeachersByFaculty(User loggedUser, int page, int perPage,String search){
        Faculty faculty = loggedUser.getFacultyOfDean();
        Long facultyId = faculty.getId();
        Long schoolId = faculty.getSchool().getId();
        Pageable pageable = PageRequest.of(page,perPage);
        Page<TeacherModel> teacherPage = teacherRepository.findTeachersWithSearchByFaculty(search,schoolId,facultyId,pageable);
        return new PaginatedTeacherResponse(
                teacherPage.getContent(),
                page,
                perPage,
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages()
        );

    }

    public TeacherDetailsResponse getTeacherDetails(Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        User user = teacher.getUser();
        List<String> studyProgramSubjects = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        for(TeacherSubject teacherSubject : teacher.getTeacherSubjects()){
            StudyProgramSubject studyProgramSubject = teacherSubject.getStudyProgramSubject();
            SchoolClass schoolClass = teacherSubject.getSchoolClass();
            String subjectName = TeacherMapper.getFullStudyProgramSubjectName(studyProgramSubject);
            studyProgramSubjects.add(subjectName);
            classes.add(schoolClass.getName());
        }
        return new TeacherDetailsResponse(
                user.getFirstName(),
                user.getLastName(),
                teacher.getSchool().getName(),
                teacher.getDepartment().getName(),
                user.getEmail(),
                teacher.getEmployeeId(),
                teacher.getAcademicTitle().toString(),
                teacher.getQualification(),
                studyProgramSubjects,
                classes

        );
    }

    public void updateTeacher(UpdateTeacherRequest request,Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        TeacherMapper.updateTeacherFromRequest(teacher,request);
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long teacherId){
        Teacher teacherToDelete = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        teacherRepository.delete(teacherToDelete);
    }



}
