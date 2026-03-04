package com.example.School_System.services;

import com.example.School_System.dto.student.PaginatedStudentResponse;
import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.dto.student.StudentModel;
import com.example.School_System.dto.mappers.StudentMapper;
import com.example.School_System.entities.School;
import com.example.School_System.entities.SchoolAdmin;
import com.example.School_System.entities.Student;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.SchoolAdminRepository;
import com.example.School_System.repositories.StudentRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final SchoolContextService schoolContextService;
    private final SchoolAdminRepository schoolAdminRepository;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, SchoolContextService schoolContextService, SchoolAdminRepository schoolAdminRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.schoolContextService = schoolContextService;
        this.schoolAdminRepository = schoolAdminRepository;
    }

    public PaginatedStudentResponse listAllStudentsForSchoolAdmin(User schoolAdmin,int page, int perPage){
        if(!schoolAdmin.isSchoolAdmin()){
            throw new AccessDeniedException("You must be school admin for this endpoint!");
        }
        Pageable pageable = PageRequest.of(page,perPage);

        School school = schoolContextService.resolveSchool(schoolAdmin);
        Long schoolId = school.getId();

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

    public StudentDetailsResponse getStudentDetailsForStudent(User user){
        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("Student with userId: " + user.getId() + " does not exist!" ));
        return getStudentDetails(student.getId());
    }

    public void updateStudent(User user,UpdateStudentRequest request, Long studentId){
        Long userId = user.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("School admin not found for user id!"));
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("School with ID" + studentId + "not found!!!"));
        if(!schoolAdmin.getSchool().getId().equals(student.getSchool().getId())){
            throw new AccessDeniedException("You do not have access to this student!!!");
        }
        StudentMapper.updateStudentFromRequest(student,request);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId,User loggedUser){
        Student student = studentRepository.findById(studentId).orElseThrow(()->new RuntimeException("Student with ID" + studentId + "not found!!!"));
        if (student.getDeleted()) {
            throw new IllegalStateException("Student already deleted!");
        }
        School school = schoolContextService.resolveSchool(loggedUser);
        if(!school.getId().equals(student.getSchool().getId())){
            throw new AccessDeniedException("You do not have permission to delete this student!");
        }
        student.delete(loggedUser);
        studentRepository.save(student);
        User user = student.getUser();
        user.delete(loggedUser);
        userRepository.save(user);
    }
}
