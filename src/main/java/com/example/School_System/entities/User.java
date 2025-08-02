package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.UserType;
import jakarta.persistence.*;

import java.security.Timestamp;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false,length = 100)
    String username;
    @Column(nullable = false)
    String email;
    @Column(name = "password_hash",nullable = false)
    String passwordHash;
    @Column(name = "first_name",nullable = false)
    String firstName;
    @Column(name = "last_name",nullable = false)
    String lastName;
    @Column(length = 20)
    String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
