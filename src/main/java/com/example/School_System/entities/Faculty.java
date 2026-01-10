package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.ApprovalStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "faculties")
@Filter(name = "deletedFilter",condition = "is_deleted = isDeleted")
public class Faculty extends SoftDeletableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_faculties_school"))
    private School school;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dean_id",
            foreignKey = @ForeignKey(name = "fk_faculty_dean"))
    private User dean;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status",nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by",nullable = false,
            foreignKey = @ForeignKey(name = "fk_faculties_created_by")
    )
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "faculty",fetch = FetchType.LAZY)
    private Set<Department> departments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="approved_by",foreignKey = @ForeignKey(name = "fk_faculties_approved_by"))
    private User approvedBy;

    public Faculty() {
    }

    public Faculty(School school, String name, String code,User createdBy) {
        this.school = school;
        this.name = name;
        this.code = code;
        this.createdBy = createdBy;
    }


    public Long getId() { return id; }
    public School getSchool() { return school; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public User getDean() { return dean; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


    public void setSchoolId(School school) { this.school = school; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDean(User dean) { this.dean = dean; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getActive() {
        return isActive;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public User getCreatedBy(){
        return createdBy;
    }

    public void approve(User user){
        this.approvalStatus = ApprovalStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
        this.approvedBy = user;
    }

    public void reject(User user,String rejectionReason){
        approvalStatus = ApprovalStatus.REJECTED;
        approvedAt = LocalDateTime.now();
        approvedBy = user;
        this.rejectionReason = rejectionReason;
    }
}