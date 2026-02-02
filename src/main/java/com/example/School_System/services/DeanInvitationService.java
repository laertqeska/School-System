package com.example.School_System.services;

import com.example.School_System.dto.dean.DeanRegistrationRequest;
import com.example.School_System.entities.DeanInvitation;
import com.example.School_System.entities.Faculty;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.InvitationStatus;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.DeanInvitationRepository;
import com.example.School_System.repositories.FacultyRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeanInvitationService {
    private final DeanInvitationRepository deanInvitationRepository;
    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;
    private final PasswordEncoder encoder;
    private final AuthorizationService authorizationService;

    public DeanInvitationService(DeanInvitationRepository deanInvitationRepository, UserRepository userRepository, FacultyRepository facultyRepository, PasswordEncoder encoder, AuthorizationService authorizationService) {
        this.deanInvitationRepository = deanInvitationRepository;
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.encoder = encoder;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void acceptInvitation(String token, DeanRegistrationRequest request){
        DeanInvitation deanInvitation = deanInvitationRepository.findByInvitationToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Dean invitation not foud for token: " + token));
        if(deanInvitation.getStatus() != InvitationStatus.PENDING){
            throw new IllegalStateException("Invitation is not pending!");
        }
        if(deanInvitation.isExpired()){
            throw new IllegalStateException("Invitation is expired!");
        }

        Faculty faculty = deanInvitation.getFaculty();
        User dean = new User(
                request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName()
        );
        authorizationService.applyRoleToUser(RoleName.DEAN,dean);
        User savedDean = userRepository.save(dean);
        faculty.setDean(savedDean);
        facultyRepository.save(faculty);
        deanInvitation.accept();
        deanInvitationRepository.save(deanInvitation);
    }
}
