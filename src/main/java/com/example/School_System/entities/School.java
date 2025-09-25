package com.example.School_System.entities;

import com.example.School_System.entities.valueObjects.SchoolType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schools")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "university_type", nullable = false)
    private SchoolType schoolType;

    @Column(name = "license_number", length = 100)
    private String licenseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rector_id", foreignKey = @ForeignKey(name = "schools_rector_id_fkey"))
    private User rector;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String email;

    @Column(name = "website_link", length = 255)
    private String websiteLink;

    @Column(name = "establishment_year")
    private Integer establishmentYear;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "fk_schools_created_by"))
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "school",fetch = FetchType.LAZY)
    private Set<AcademicYear> academicYears = new HashSet<>();

    @OneToMany(mappedBy = "school",fetch = FetchType.LAZY)
    private Set<Faculty> faculties = new HashSet<>();

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    @OneToOne(mappedBy = "school", fetch = FetchType.LAZY)
    private SchoolAdmin schoolAdmin;


    public School() {}

    public School(
            String name,
            SchoolType schoolType,
            String licenseNumber,
            User rector,
            String address,
            String city,
            String postalCode,
            String phone,
            String email,
            String websiteLink,
            Integer establishmentYear,
            Boolean isActive,
            User createdBy
    ) {
        this.name = name;
        this.schoolType = schoolType;
        this.licenseNumber = licenseNumber;
        this.rector = rector;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.websiteLink = websiteLink;
        this.establishmentYear = establishmentYear;
        this.isActive = (isActive != null) ? isActive : true;
        this.createdBy = createdBy;
    }


    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SchoolType getSchoolType() { return schoolType; }
    public void setSchoolType(SchoolType schoolType) { this.schoolType = schoolType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public User getRector() { return rector; }
    public void setRector(User rector) { this.rector = rector; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWebsiteLink() { return websiteLink; }
    public void setWebsiteLink(String websiteLink) { this.websiteLink = websiteLink; }

    public Integer getEstablishmentYear() { return establishmentYear; }
    public void setEstablishmentYear(Integer establishmentYear) { this.establishmentYear = establishmentYear; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
