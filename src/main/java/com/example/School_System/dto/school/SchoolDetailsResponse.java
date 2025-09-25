package com.example.School_System.dto.school;

public class SchoolDetailsResponse {
    private String name;
    private String universityType;
    private String licenseNumber;
    private String rectorName;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;
    private String websiteLink;
    private int establishmentYear;

    public SchoolDetailsResponse(String name, String universityType, String licenseNumber, String rectorName, String address, String city, String postalCode, String phone, String email, String websiteLink, int establishmentYear) {
        this.name = name;
        this.universityType = universityType;
        this.licenseNumber = licenseNumber;
        this.rectorName = rectorName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.websiteLink = websiteLink;
        this.establishmentYear = establishmentYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversityType() {
        return universityType;
    }

    public void setUniversityType(String universityType) {
        this.universityType = universityType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getRectorName() {
        return rectorName;
    }

    public void setRectorName(String rectorName) {
        this.rectorName = rectorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
}
