package com.example.School_System.services;

import com.example.School_System.dto.student.PaginatedStudentResponse;
import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.dto.student.StudentModel;
import com.example.School_System.dto.mappers.StudentMapper;
import com.example.School_System.entities.School;
import com.example.School_System.entities.Student;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.StudentRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public PaginatedStudentResponse listAllStudentsForSchoolAdmin(User schoolAdmin,int page, int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        Long schoolId = schoolAdmin.getSchool().getId();
        Page<StudentModel> studentPage = studentRepository.findStudentModelsBySchoolId(schoolId,pageable);
        return new PaginatedStudentResponse(studentPage.getContent(),page,perPage,studentPage.getTotalElements(),studentPage.getTotalPages());
    }

    public StudentDetailsResponse getStudentDetails(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        LocalDate localBirthDate = student.getDateOfBirth().toLocalDate();
        LocalDate today = LocalDate.now();
        int studentAge = Period.between(localBirthDate,today).getYears();

        return new StudentDetailsResponse(student.getUser().getFirstName(),
                student.getUser().getLastName(),
                student.getSchool().getName(),
                studentAge,
                student.getGender().toString(),
                student.getStudentId(),
                student.getStudyProgram().getName(),
                student.getDateOfBirth(),
                student.getPersonalNumber(),
                student.getStatus().toString(),
                student.getEnrollmentDate(),
                student.getCurrentYear(),
                student.getCurrentSemester()
                );
    }

    public StudentDetailsResponse getStudentDetailsForStudent(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username: " + username + " does not exist!"));
        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("Student with userId: " + user.getId() + " does not exist!" ));
        return getStudentDetails(student.getId());
    }

    public void updateStudent(UpdateStudentRequest request, Long studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("School with ID" + studentId + "not found!!!"));
        StudentMapper.updateStudentFromRequest(student,request);
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Student with ID" + studentId + "not found!!!"));
        studentRepository.delete(student);
    }
}
