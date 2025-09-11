package com.example.School_System.dto;

import com.example.School_System.entities.valueObjects.UniversityType;

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
}
