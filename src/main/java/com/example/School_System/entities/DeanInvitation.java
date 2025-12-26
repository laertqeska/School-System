package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.InvitationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="dean_invitation")
public class DeanInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dean_email")
    private String deanEmail;

    @Column(name = "dean_full_name")
    private String deanFullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="facultyId",nullable = false)
    private Faculty faculty;

    @Column(nullable = false,unique = true)
    private String invitationToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="invited_by")
    private User invitedBy;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public DeanInvitation(){}
    public DeanInvitation(String deanEmail,String deanFullName,Faculty faculty,User invitedBy){
        this.deanEmail = deanEmail;
        this.deanFullName = deanFullName;
        this.faculty = faculty;
        this.invitationToken = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusDays(7);
    }

    public Long getId() {
        return id;
    }

    public String getDeanEmail() {
        return deanEmail;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getInvitationToken() {
        return invitationToken;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getDeanFullName() {
        return deanFullName;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void accept(){
        this.status = InvitationStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
    }
}
