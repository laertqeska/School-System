package com.example.School_System.services;

import com.example.School_System.entities.Faculty;
import com.example.School_System.entities.FacultyApprovalToken;
import com.example.School_System.repositories.FacultyApprovalTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FacultyApprovalService {
    private final FacultyApprovalTokenRepository facultyApprovalTokenRepository;
    private final EmailService emailService;

    public FacultyApprovalService(FacultyApprovalTokenRepository facultyApprovalTokenRepository, EmailService emailService){
        this.facultyApprovalTokenRepository = facultyApprovalTokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void createFacultyApproval(Faculty faculty){
        FacultyApprovalToken facultyApprovalToken = new FacultyApprovalToken(faculty);
        FacultyApprovalToken savedFacultyApprovalToken = facultyApprovalTokenRepository.save(facultyApprovalToken);
        emailService.sendFacultyApproval(savedFacultyApprovalToken);
    }
}
