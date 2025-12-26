package com.example.School_System.services;

import com.example.School_System.entities.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendRectorInvitation(RectorInvitation rectorInvitation){
        String invitationLink = frontendUrl + "/rector-registration.html?token=" + rectorInvitation.getInvitationToken();

        String subject = "Invitation to become the Rector of " + rectorInvitation.getSchool().getName();

        String body = String.format("""
            Dear %s,
            
            You have been invited to become the Rector of %s.
            
            Please click the link below to create your account and accept this invitation:
            %s
            
            This invitation will expire on %s.
            
            If you did not expect this invitation, please ignore this email.
            
            Best regards,
            School System Team
            """,
                rectorInvitation.getRectorFullName(),
                rectorInvitation.getSchool().getName(),
                invitationLink,
                rectorInvitation.getExpiresAt().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' HH:mm"))
        );


        sendSimpleEmail(rectorInvitation.getRectorEmail(),subject,body);
    }

    public void sendFacultyApproval(FacultyApprovalToken facultyApprovalToken){
        String approvalLink = frontendUrl + "/rector/faculty-approval/" + facultyApprovalToken.getToken();
        String rejectionLInk = frontendUrl + "/rector/faculty-rejection/" + facultyApprovalToken.getToken();
        Faculty faculty = facultyApprovalToken.getFaculty();
        String subject = "Faculty Approval Required: " + faculty.getName();
        String body = String.format("""
            Dear Rector,
            
            A new faculty has been created at %s and requires your approval:
            
            Faculty Details:
            - Name: %s
            - Code: %s
            - Created by: %s
            - Created on: %s
            
            Please review and take action:
            
            ✅ To APPROVE, click here:
            %s
            
            ❌ To REJECT, click here:
            %s
            
            Note: These links will expire in 7 days.
            
            Best regards,
            School Management System
            """,
                faculty.getSchool().getName(),
                faculty.getName(),
                faculty.getCode(),
                faculty.getCreatedBy().getFirstName() + " " + faculty.getCreatedBy().getLastName(),
                faculty.getCreatedAt(),
                approvalLink,
                rejectionLInk
        );
        User rector = facultyApprovalToken.getFaculty().getSchool().getRector();
        sendSimpleEmail(rector.getEmail(),subject,body);
    }

    public void sendDeanInvitation(DeanInvitation deanInvitation){
        String facultyName = deanInvitation.getFaculty().getName();
        String invitationLink =  frontendUrl + "/dean-registration.html?token=" + deanInvitation.getInvitationToken();
        String subject = "Invitation to become the dean of " + facultyName;

        String body = String.format("""
            Dear %s,
            
            You have been invited to become the Dean of %s.
            
            Please click the link below to create your account and accept this invitation:
            %s
            
            This invitation will expire on %s.
            
            If you did not expect this invitation, please ignore this email.
            
            Best regards,
            School System Team
            """,
                deanInvitation.getDeanFullName(),
                facultyName,
                invitationLink,
                deanInvitation.getExpiresAt().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' HH:mm"))
        );
        sendSimpleEmail(
                deanInvitation.getDeanEmail(),
                subject,
                body
        );
    }

    //TODO: Add try catch for this
    private void sendSimpleEmail(String recipient, String subject,String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(fromEmail);

        mailSender.send(mailMessage);
    }
}
