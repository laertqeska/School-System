package com.example.School_System.dto.school;

import com.example.School_System.entities.valueObjects.SchoolType;

public class CreateSchoolRequest {
    private String name;
    private String code;
    private SchoolType schoolType;
    private String address;
    private String city;
    private String phone;
    private String email;
    private Long rectorId;
    private String licenseNumber;
    private String postalCode;
    private String websiteLink;
    private int establishmentYear;
    private String qrCode;
    private String rectorFullName;
    private String rectorEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRectorId() {
        return rectorId;
    }

    public void setRectorId(Long rectorId) {
        this.rectorId = rectorId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public int getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(int establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getRectorFullName() {
        return rectorFullName;
    }

    public void setRectorFullName(String rectorFullName) {
        this.rectorFullName = rectorFullName;
    }

    public String getRectorEmail() {
        return rectorEmail;
    }

    public void setRectorEmail(String rectorEmail) {
        this.rectorEmail = rectorEmail;
    }
}
