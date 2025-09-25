package com.example.School_System.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "super_admins")
public class SuperAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_super_admin_user"))
    private User user;

    @Column(columnDefinition = "JSON")
    private String permissions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SuperAdmin() {
    }

    public SuperAdmin(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getPermissions() { return permissions; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUser(User user) { this.user = user; }
    public void setPermissions(String permissions) { this.permissions = permissions; }
}