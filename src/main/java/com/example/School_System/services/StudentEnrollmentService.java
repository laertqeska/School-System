package com.example.School_System.services;

import com.example.School_System.dto.student.CreateStudentRequest;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@Transactional
public class StudentEnrollmentService {

    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final SchoolContextService schoolContextService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentEnrollmentService(StudentRepository studentRepository,
                                    SchoolRepository schoolRepository, SchoolClassRepository schoolClassRepository,
                                    StudyProgramRepository studyProgramRepository, SchoolContextService schoolContextService,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.schoolContextService = schoolContextService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createStudent(CreateStudentRequest request,User loggedUser) {
        School school = schoolContextService.resolveSchool(loggedUser);

        StudyProgram studyProgram = studyProgramRepository.findById(request.getStudyProgramId())
                .orElseThrow(() -> new EntityNotFoundException("Study program not found with ID: " + request.getStudyProgramId()));
        SchoolClass schoolClass = schoolClassRepository.findById(request.getSchoolClassId()).orElseThrow(()->new EntityNotFoundException("School class not found with ID: " + request.getSchoolClassId()));

            validateStudentCreation(request, school,studyProgram,schoolClass);

        User user = createUser(request);
        User savedUser = userRepository.save(user);


        assignStudentRole(savedUser);

        Student student = createStudentEntity(request, savedUser, school, studyProgram,schoolClass);
        studentRepository.save(student);

        return student.getId();
    }

    private User createUser(CreateStudentRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName()
        );

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        return user;
    }

    private void assignStudentRole(User user) {
        Role studentRole = roleRepository.findByName(RoleName.STUDENT)
                .orElseThrow(() -> new IllegalStateException("STUDENT role not found in database"));

        user.getRoles().add(studentRole);
        userRepository.save(user);
    }

    private Student createStudentEntity(CreateStudentRequest request, User user,
                                        School school, StudyProgram studyProgram,SchoolClass schoolClass) {

        String studentId = request.getStudentId();

        if (studentRepository.existsByStudentId(studentId)) {
            throw new IllegalArgumentException("Student ID already exists: " + studentId);
        }

        Date enrollmentDate = request.getEnrollmentDate() != null ?
                request.getEnrollmentDate() :
                new Date(System.currentTimeMillis());

        return new Student(
                user,
                school,
                studyProgram,
                schoolClass,
                studentId,
                enrollmentDate,
                request.getStatus(),
                request.getPersonalNumber(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getAddress()
        );
    }

    private void validateStudentCreation(CreateStudentRequest request, School school,StudyProgram studyProgram,SchoolClass schoolClass) {
        if (!school.getIsActive()) {
            throw new IllegalArgumentException("Cannot enroll student in inactive school!");
        }

        if(!studyProgram.getDepartment().getFaculty().getSchool().getId().equals(school.getId())){
            throw new IllegalArgumentException("Cannot enroll student in non existing study program!");
        }

        if(!schoolClass.getStudyProgram().getDepartment().getFaculty().getSchool().getId().equals(school.getId())){
            throw new IllegalArgumentException("School class not found for school with ID: " + school.getId());
        }

        if(!schoolClass.getStudyProgram().getId().equals(studyProgram.getId())){
            throw new IllegalArgumentException("School class does not belong to the selected study program!");
        }
    }
}