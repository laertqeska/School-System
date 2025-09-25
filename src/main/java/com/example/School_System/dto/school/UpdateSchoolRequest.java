package com.example.School_System.dto.school;

import com.example.School_System.entities.valueObjects.SchoolType;

public class UpdateSchoolRequest {

    private String name;
    private SchoolType schoolType;
    private String licenseNumber;
    private Long rectorId;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;
    private String websiteLink;
    private Integer establishmentYear;
    private Boolean isActive;

    public UpdateSchoolRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SchoolType getUniversityType() { return schoolType; }
    public void setUniversityType(SchoolType schoolType) { this.schoolType = schoolType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public Long getRectorId() { return rectorId; }
    public void setRectorId(Long rectorId) { this.rectorId = rectorId; }

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
}
