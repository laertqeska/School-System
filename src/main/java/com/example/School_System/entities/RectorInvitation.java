package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.InvitationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="rector_invitation")
public class RectorInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rectorEmail;

    @Column(nullable = false)
    private String rectorFullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id",nullable = false)
    private School school;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="invited_by")
    private User invitedBy;

    @Column(nullable = false,unique = true)
    private String invitationToken;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    public RectorInvitation() {
    }

    public RectorInvitation(String rectorEmail,String rectorFullName, School school, InvitationStatus status, User invitedBy) {
        this.rectorEmail = rectorEmail;
        this.rectorFullName = rectorFullName;
        this.school = school;
        this.status = status;
        this.invitedBy = invitedBy;
        this.invitationToken = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusDays(7);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRectorEmail() {
        return rectorEmail;
    }

    public void setRectorEmail(String rectorEmail) {
        this.rectorEmail = rectorEmail;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public User getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getRectorFullName() {
        return rectorFullName;
    }

    public String getInvitationToken() {
        return invitationToken;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void accept(){
        this.status = InvitationStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
    }
}
