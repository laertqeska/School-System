package com.example.School_System.services;


import com.example.School_System.dto.*;
import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.SchoolRepository;
import com.example.School_System.repositories.UserRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<PaginatedSchoolModel> listSchools(int page,int perPage){
        Pageable pageable = PageRequest.of(page,perPage);
        Page<School> schoolPage =  schoolRepository.findAll(pageable);
        return
    }

    public Long createSchool(CreateSchoolRequest request, User createdBy) throws RuntimeException{
        User rector = userRepository.findById(request.getRectorId()).orElseThrow(()->new RuntimeException("Rector with ID" + request.getRectorId() + "not found!!!"));

        School school = new School(
                request.getName(),
                request.getUniversityType(),
                request.getLicenseNumber(),
                rector,
                request.getAddress(),
                request.getCity(),
                request.getPostalCode(),
                request.getPhone(),
                request.getEmail(),
                request.getWebsiteLink(),
                request.getEstablishmentYear(),
                true,
                createdBy
        );

        schoolRepository.save(school);
        return school.getId();
    }

    public SchoolDetailsResponse getSchoolDetails(Long id){
        School school = schoolRepository.findById(id).orElseThrow(()->new RuntimeException("School with ID" + id + "not found!!!"));
        SchoolDetailsResponse response = new SchoolDetailsResponse(
                school.getName(),
                school.getUniversityType().toString(),
                school.getLicenseNumber(),
                school.getRector().getFirstName() + " " + school.getRector().getLastName(),
                school.getAddress(),
                school.getCity(),
                school.getPostalCode(),
                school.getPhone(),
                school.getEmail(),
                school.getWebsiteLink(),
                school.getEstablishmentYear()
        );
        return response;
    }

    public void updateSchool(UpdateSchoolRequest request,Long schoolId){
        School school = schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School with ID" + schoolId + "not found!!!"));
        User rector = null;
        if(request.getRectorId() != null){
            rector = userRepository.findById(request.getRectorId()).orElseThrow(() -> new RuntimeException("User with ID" + request.getRectorId() + "not found!!!"));
        }
        SchoolMapper.updateSchoolFromDto(request,school,rector);
        schoolRepository.save(school);
    }

    public void deleteSchool(Long schoolId){
        School school = schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School with ID" + schoolId + "not found!!!"));
        schoolRepository.delete(school);
    }
}
