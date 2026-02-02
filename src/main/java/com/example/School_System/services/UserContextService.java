package com.example.School_System.services;

import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
 import com.example.School_System.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final FacultyRepository facultyRepository;
    private final SchoolRepository schoolRepository;

    public UserContextService(TeacherRepository teacherRepository, StudentRepository studentRepository, SchoolAdminRepository schoolAdminRepository, FacultyRepository facultyRepository, SchoolRepository schoolRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.facultyRepository = facultyRepository;
        this.schoolRepository = schoolRepository;
    }

    public School resolveSchool(User user) {

        if (user.isRector()) {
            return schoolRepository.findSchoolForRector(user.getId())
                    .orElseThrow(() -> new IllegalStateException("Rector has no school"));
        }

        if (user.isSchoolAdmin()) {
            return schoolAdminRepository.findSchoolForSchoolAdmin(user.getId())
                    .orElseThrow(() -> new IllegalStateException("School admin has no school"));
        }

        if (user.isDean()) {
            return facultyRepository.findSchoolForDean(user.getId())
                    .orElseThrow(() -> new IllegalStateException("Dean has no school"));
        }

        if (user.isTeacher()) {
            return teacherRepository.findSchoolForTeacher(user.getId())
                    .orElseThrow(() -> new IllegalStateException("Teacher has no school"));
        }

        if (user.isStudent()) {
            return studentRepository.findSchoolForStudent(user.getId())
                    .orElseThrow(() -> new IllegalStateException("Student has no school"));
        }

        throw new IllegalStateException("User has no school");
    }
}
