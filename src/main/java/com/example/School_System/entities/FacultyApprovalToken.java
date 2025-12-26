package com.example.School_System.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="faculty_approval_tokens")
public class FacultyApprovalToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique=true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="faculty_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_faculty_approval_tokens_faculty")
    )
    private Faculty faculty;

    @Column(nullable = false,name="expires_at")
    private LocalDateTime expiresAt;

    @Column(nullable = false,name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false,name = "is_used")
    private Boolean isUsed;

    public FacultyApprovalToken(){}

    public FacultyApprovalToken(Faculty faculty){
        this.token = UUID.randomUUID().toString();
        this.faculty = faculty;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusDays(7);
        this.isUsed = false;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired(){
        return expiresAt.isBefore(LocalDateTime.now());
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void use(){
        isUsed = true;
    }
}
