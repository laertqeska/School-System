package com.example.School_System.services;

import com.example.School_System.dto.rector.PaginatedRectorFacultiesResponse;
import com.example.School_System.dto.rector.RectorFacultiesModel;
import com.example.School_System.dto.rector.RectorInviteDeanRequest;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.ApprovalStatus;
import com.example.School_System.entities.valueObjects.InvitationStatus;
import com.example.School_System.repositories.DeanInvitationRepository;
import com.example.School_System.repositories.FacultyApprovalTokenRepository;
import com.example.School_System.repositories.FacultyRepository;
import com.example.School_System.repositories.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class RectorService {
    private final FacultyRepository facultyRepository;
    private final SchoolRepository schoolRepository;
    private final FacultyApprovalTokenRepository facultyApprovalTokenRepository;
    private final EmailService emailService;
    private final DeanInvitationRepository deanInvitationRepository;

    public RectorService(FacultyRepository facultyRepository, SchoolRepository schoolRepository, FacultyApprovalTokenRepository facultyApprovalTokenRepository, EmailService emailService, DeanInvitationRepository deanInvitationRepository){
        this.facultyRepository = facultyRepository;
        this.schoolRepository = schoolRepository;
        this.facultyApprovalTokenRepository = facultyApprovalTokenRepository;
        this.emailService = emailService;
        this.deanInvitationRepository = deanInvitationRepository;
    }

    public PaginatedRectorFacultiesResponse getFacultiesForRector(User rector,int page, int perPage){
        School school = schoolRepository.findByRector(rector)
                .orElseThrow(()->new EntityNotFoundException("School not found with rector id:" + rector.getId()));
        Pageable pageable = PageRequest.of(page,perPage);
        Page<RectorFacultiesModel> facultiesPage = facultyRepository.getFacultiesForRector(pageable,school.getId(),ApprovalStatus.APPROVED);

        return new PaginatedRectorFacultiesResponse(
                facultiesPage.getContent(),
                page,
                perPage,
                facultiesPage.getTotalElements(),
                facultiesPage.getTotalPages()
        );
    }
    @Transactional
    public void approveFaculty(String token,User rector){
        FacultyApprovalToken facultyApprovalToken = facultyApprovalTokenRepository.findByToken(token)
                .orElseThrow(()-> new EntityNotFoundException("Faculty approval token not found with token: " + token));
        Faculty faculty = facultyApprovalToken.getFaculty();
        validateFacultyApprovalToken(facultyApprovalToken,faculty);
        faculty.approve(rector);
        facultyApprovalToken.use();
    }

    @Transactional
    public void rejectFaculty(String token,User rector,String rejectionReason){
        FacultyApprovalToken facultyApprovalToken = facultyApprovalTokenRepository.findByToken(token)
                .orElseThrow(()-> new EntityNotFoundException("Faculty approval token not found with token: " + token));
        Faculty faculty = facultyApprovalToken.getFaculty();
        validateFacultyApprovalToken(facultyApprovalToken,faculty);
        faculty.reject(rector,rejectionReason);
        facultyApprovalToken.use();
    }

    private void validateFacultyApprovalToken(FacultyApprovalToken token,Faculty faculty){
        if(token.getUsed()){
            throw new IllegalStateException("This approval link has already been used!");
        }

        if(token.isExpired()){
            throw new IllegalStateException("This approval link has expired");
        }

        if(faculty.getApprovalStatus() != ApprovalStatus.PENDING){
            throw new IllegalStateException(
                    "Faculty is no longer pending approval (status: " +
                            faculty.getApprovalStatus() + ")"
            );
        }
    }


    public String createAndSendDeanInvitation(RectorInviteDeanRequest request,User loggedUser){
        Long facultyId = request.getFacultyId();
        String deanEmail = request.getDeanEmail();
        boolean pendingEmailAlreadyExists  = deanInvitationRepository.existsByDeanEmailAndStatus(deanEmail, InvitationStatus.PENDING);
        if(pendingEmailAlreadyExists){
            throw new IllegalStateException("Invitation already sent to this email!");
        }
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new EntityNotFoundException("Faculty not found for ID: " + facultyId));
        DeanInvitation deanInvitation = new DeanInvitation(
                deanEmail,
                request.getDeanFullName(),
                faculty,
                loggedUser
        );
        DeanInvitation savedInvitation = deanInvitationRepository.save(deanInvitation);
        emailService.sendDeanInvitation(deanInvitation);
        return savedInvitation.getInvitationToken();
    }

    public void getRectors(int page,int perPage,User superAdmin){

    }

}
