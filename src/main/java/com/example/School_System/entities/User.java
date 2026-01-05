package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.RoleName;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Student student;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Teacher teacher;

    @OneToOne(mappedBy = "dean",fetch = FetchType.LAZY)
    private Faculty facultyOfDean;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private SchoolAdmin schoolAdmin;

    @OneToMany(mappedBy = "rector", fetch = FetchType.LAZY)
    private Set<School> rectorOfSchools = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<School> createdSchools = new HashSet<>();

    // ADD: Created faculties (for audit trail)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Faculty> createdFaculties = new HashSet<>();

    // ADD: Created departments (for audit trail)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Department> createdDepartments = new HashSet<>();

    // ADD: Created subjects (for audit trail)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Subject> createdSubjects = new HashSet<>();

    // ADD: Created study programs (for audit trail)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<StudyProgram> createdStudyPrograms = new HashSet<>();

    public User(){}

    public User(String username, String email, String passwordHash, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean hasRole(RoleName roleName) {
        return roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    public boolean isSuperAdmin() {
        return hasRole(RoleName.SUPER_ADMIN);
    }

    public boolean isRector() {
        return hasRole(RoleName.RECTOR);
    }

    public boolean isSchoolAdmin() {
        return hasRole(RoleName.SCHOOL_ADMIN);
    }

    public boolean isDean() {
        return hasRole(RoleName.DEAN);
    }

    public boolean isTeacher() {
        return hasRole(RoleName.TEACHER);
    }

    public boolean isStudent() {
        return hasRole(RoleName.STUDENT);
    }

    public RoleName getPrimaryRole() {
        // Return highest priority role
        if (isSuperAdmin()) return RoleName.SUPER_ADMIN;
        if (isRector()) return RoleName.RECTOR;
        if (isSchoolAdmin()) return RoleName.SCHOOL_ADMIN;
        if (isDean()) return RoleName.DEAN;
        if (isTeacher()) return RoleName.TEACHER;
        if (isStudent()) return RoleName.STUDENT;
        return null;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public Boolean getActive() {
        return isActive;
    }

    public Student getStudent() {
        return student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Faculty getFacultyOfDean() {
        return facultyOfDean;
    }

    public SchoolAdmin getSchoolAdmin() {
        return schoolAdmin;
    }

    public Set<School> getRectorOfSchools() {
        return rectorOfSchools;
    }

    public Set<School> getCreatedSchools() {
        return createdSchools;
    }

    public Set<Faculty> getCreatedFaculties() {
        return createdFaculties;
    }

    public Set<Department> getCreatedDepartments() {
        return createdDepartments;
    }

    public Set<Subject> getCreatedSubjects() {
        return createdSubjects;
    }

    public Set<StudyProgram> getCreatedStudyPrograms() {
        return createdStudyPrograms;
    }

    public School getSchool() {
        if (isRector() && !rectorOfSchools.isEmpty()) {
            return rectorOfSchools.iterator().next();
        }
        if (isSchoolAdmin() && schoolAdmin != null) {
            return schoolAdmin.getSchool();
        }
        if (isDean() && facultyOfDean != null) {
            return facultyOfDean.getSchool();
        }
        if (isTeacher() && teacher != null) {
            return teacher.getSchool();
        }
        if (isStudent() && student != null) {
            return student.getSchool();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
