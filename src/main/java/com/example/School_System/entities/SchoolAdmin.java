package com.example.School_System.entities;

import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "school_admins")
@Filter(name="deletedFilter",condition = "is_deleted = :isDeleted")
public class SchoolAdmin extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_school_admin_user"))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_school_admin_school")
    )
    private School school;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SchoolAdmin() {
    }

    public SchoolAdmin(User user, School school) {
        this.user = user;
        this.school = school;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public School getSchool() { return school; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUser(User user) { this.user = user; }
    public void setSchool(School school) { this.school = school; }
}