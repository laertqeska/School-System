package com.example.School_System.services;


import com.example.School_System.dto.schoolAdmin.CreateSchoolAdminRequest;
import com.example.School_System.entities.School;
import com.example.School_System.entities.SchoolAdmin;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.SchoolAdminRepository;
import com.example.School_System.repositories.SchoolRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SchoolAdminCreationService {
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolAdminRepository schoolAdminRepository;
    private final AuthorizationService authorizationService;

    public SchoolAdminCreationService(SchoolRepository schoolRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, SchoolAdminRepository schoolAdminRepository, AuthorizationService authorizationService) {
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.schoolAdminRepository = schoolAdminRepository;
        this.authorizationService = authorizationService;
    }


    public Long createAdmin(User loggedUser, CreateSchoolAdminRequest request){
        School school = schoolRepository.findById(request.getSchoolId()).orElseThrow(()-> new EntityNotFoundException("School not found with Id: " + request.getSchoolId()));
        User user = createUser(request);
        authorizationService.applyRoleToUser(RoleName.SCHOOL_ADMIN,user);
        User savedUser = userRepository.save(user);
        SchoolAdmin schoolAdmin = new SchoolAdmin(
                savedUser,
                school
        );
        SchoolAdmin savedSchoolAdmin = schoolAdminRepository.save(schoolAdmin);
        return savedSchoolAdmin.getId();
    }

    private User createUser(CreateSchoolAdminRequest request){
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

        userRepository.save(user);

        return user;
    }
}
