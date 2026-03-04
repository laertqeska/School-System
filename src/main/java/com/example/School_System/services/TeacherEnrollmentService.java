package com.example.School_System.services;

import com.example.School_System.dto.teacher.CreateTeacherRequest;
import com.example.School_System.dto.teacher.SubjectAssignment;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@Service
public class TeacherEnrollmentService {

    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SchoolAdminRepository schoolAdminRepository;


    public TeacherEnrollmentService(SchoolRepository schoolRepository, UserRepository userRepository, TeacherRepository teacherRepository, StudyProgramRepository studyProgramRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, SubjectRepository subjectRepository, TeacherSubjectRepository teacherSubjectRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, SchoolClassRepository schoolClassRepository, AcademicYearRepository academicYearRepository, SchoolAdminRepository schoolAdminRepository) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.academicYearRepository = academicYearRepository;
        this.schoolAdminRepository = schoolAdminRepository;
    }

    public Long createTeacher(CreateTeacherRequest request, User loggedUser){
        Long userId = loggedUser.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(userId)
                .orElseThrow(()->new EntityNotFoundException("School Admin not found for user id: " + userId));
        Long schoolId = schoolAdmin.getSchool().getId();
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with ID: " + schoolId));
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " + request.getDepartmentId()));

        if(!department.getFaculty().getSchool().getId().equals(school.getId())){
            throw new AccessDeniedException("Department does not belong to your school!!!");
        }
        User user = createUser(request);
        User savedUser = userRepository.save(user);
        assignTeacherRole(savedUser);

        Teacher teacher = createTeacherEntity(request,savedUser,school,department);
        Teacher savedTeacher = teacherRepository.save(teacher);

        return savedTeacher.getId();
    }

    private User createUser(CreateTeacherRequest request) {
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

    private void assignTeacherRole(User user) {
        Role teacherRole = roleRepository.findByName(RoleName.TEACHER)
                .orElseThrow(() -> new IllegalStateException("TEACHER role not found in database"));

        user.getRoles().add(teacherRole);
        userRepository.save(user);
    }

    private Teacher createTeacherEntity(CreateTeacherRequest request, User user, School school, Department department) {

        return new Teacher(
                user,
                school,
                department,
                request.getEmployeeId(),
                request.getAcademicTitle(),
                request.getQualification(),
                request.getIsActive() != null ? request.getIsActive() : true
        );
    }
}
