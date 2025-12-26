package com.example.School_System.services;

import com.example.School_System.dto.rector.RectorRegistrationRequest;
import com.example.School_System.entities.RectorInvitation;
import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.InvitationStatus;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.RectorInvitationRepository;
import com.example.School_System.repositories.SchoolRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RectorInvitationService {
    private final RectorInvitationRepository rectorInvitationRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;
    private final EmailService emailService;

    public RectorInvitationService(RectorInvitationRepository rectorInvitationRepository, UserRepository userRepository, SchoolRepository schoolRepository, PasswordEncoder passwordEncoder, AuthorizationService authorizationService, EmailService emailService) {
        this.rectorInvitationRepository = rectorInvitationRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorizationService = authorizationService;
        this.emailService = emailService;
    }

    @Transactional
    public void createInvitation(Long schoolId, String rectorFullName, String rectorEmail, User invitedBy){
        boolean pendingEmailAlreadyExists = rectorInvitationRepository.existsByRectorEmailAndStatus(rectorEmail, InvitationStatus.PENDING);
        if(pendingEmailAlreadyExists){
            throw new IllegalStateException("Invitation already sent to this email!");
        }
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException("School not found with ID: " + schoolId));
        RectorInvitation rectorInvitation = new RectorInvitation(
                rectorEmail,
                rectorFullName,
                school,
                InvitationStatus.PENDING,
                invitedBy
        );
        RectorInvitation invitation = rectorInvitationRepository.save(rectorInvitation);

        emailService.sendRectorInvitation(invitation);
    }

    @Transactional
    public void acceptInvitation(String token, RectorRegistrationRequest request){
        RectorInvitation rectorInvitation = rectorInvitationRepository.findByInvitationToken(token)
                .orElseThrow(()->new EntityNotFoundException("Rector invitation not found for token: " + token));

        if(rectorInvitation.getStatus() != InvitationStatus.PENDING){
            throw new IllegalStateException("Invitation is not pending!");
        }

        if(rectorInvitation.isExpired()){
            throw new IllegalStateException("Invitation is expired!");
        }

        School school = rectorInvitation.getSchool();
        User rector = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName()
        );
        authorizationService.applyRoleToUser(RoleName.RECTOR,rector);
        User savedRector = userRepository.save(rector);
        school.setRector(savedRector);
        schoolRepository.save(school);

        rectorInvitation.accept();
        rectorInvitationRepository.save(rectorInvitation);
    }


}
