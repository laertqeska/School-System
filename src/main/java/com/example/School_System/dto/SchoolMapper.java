package com.example.School_System.dto;

import com.example.School_System.entities.School;
import com.example.School_System.entities.User;

public class SchoolMapper {
    public static void updateSchoolFromDto(UpdateSchoolRequest dto, School school, User rector) {
        if (dto.getName() != null) school.setName(dto.getName());
        if (dto.getUniversityType() != null) school.setUniversityType(dto.getUniversityType());
        if (dto.getAddress() != null) school.setAddress(dto.getAddress());
        if (dto.getCity() != null) school.setCity(dto.getCity());
        if (dto.getPhone() != null) school.setPhone(dto.getPhone());
        if (dto.getEmail() != null) school.setEmail(dto.getEmail());
        if (dto.getWebsiteLink() != null) school.setWebsiteLink(dto.getWebsiteLink());
        if (dto.getLicenseNumber() != null) school.setLicenseNumber(dto.getLicenseNumber());
        if (dto.getPostalCode() != null) school.setPostalCode(dto.getPostalCode());
        if (dto.getEstablishmentYear() != null) school.setEstablishmentYear(dto.getEstablishmentYear());
        if (dto.getIsActive() != null) school.setIsActive(dto.getIsActive());
        if (rector != null) school.setRector(rector);
    }
}
