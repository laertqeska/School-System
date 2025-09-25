package com.example.School_System.services;

import com.example.School_System.dto.mappers.TeacherMapper;
import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.dto.teacher.TeacherDetailsResponse;
import com.example.School_System.dto.teacher.TeacherModel;
import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.SchoolClassRepository;
import com.example.School_System.repositories.SubjectRepository;
import com.example.School_System.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, SchoolClassRepository schoolClassRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public PaginatedTeacherResponse listTeachers(Long schoolId,int page,int perPage,String search){
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
                page,
                perPage,
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages()
        );
    }

    public TeacherDetailsResponse getTeacherDetails(Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        User user = teacher.getUser();
        List<String> subjects = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        for(TeacherSubject teacherSubject : teacher.getTeacherSubjects()){
            Subject subject = subjectRepository.findById(teacherSubject.getSubjectId()).orElseThrow(() -> new EntityNotFoundException("Subject with ID: " + teacherSubject.getSubjectId() + " does not exist!!!"));
            SchoolClass schoolClass = schoolClassRepository.findById(teacherSubject.getClassId()).orElseThrow(()-> new EntityNotFoundException("School class with ID: " + teacherSubject.getClassId() + " does not exist!!!"));
            subjects.add(subject.getName());
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
                subjects,
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
