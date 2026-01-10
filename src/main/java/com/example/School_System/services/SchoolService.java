package com.example.School_System.services;


import com.example.School_System.dto.mappers.SchoolMapper;
import com.example.School_System.dto.school.*;
import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.SchoolRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RectorInvitationService rectorInvitationService;

    public PaginatedSchoolResponse listSchools(int page, int perPage){
        if(page > 0) page--;
        else throw new RuntimeException("Page parameter cannot be smaller or equal to 0!!!");
        Pageable pageable = PageRequest.of(page,perPage);
        Page<School> schoolPage = schoolRepository.findAll(pageable);
        List<SchoolModel> response = new ArrayList<>();
        for(School school : schoolPage.getContent()){
            SchoolModel schoolModel = new SchoolModel(
                    school.getId(),
                    school.getName(),
                    school.getCity(),
                    school.getPhone(),
                    school.getEmail()
            );
            response.add(schoolModel);
        }
        return new PaginatedSchoolResponse(
                response,
                page,
                perPage,
                schoolPage.getTotalElements(),
                schoolPage.getTotalPages()
        );
    }

    @Transactional
    public Long createSchool(CreateSchoolRequest request, User createdBy) throws RuntimeException{
        School school = new School(
                request.getName(),
                request.getSchoolType(),
                request.getLicenseNumber(),
                null,
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

        rectorInvitationService.createInvitation(school.getId(),request.getRectorFullName(),request.getRectorEmail(),createdBy);

        return school.getId();
    }

    public SchoolDetailsResponse getSchoolDetails(Long id){
        School school = schoolRepository.findById(id).orElseThrow(()->new RuntimeException("School with ID" + id + "not found!!!"));
        return new SchoolDetailsResponse(
                school.getName(),
                school.getSchoolType().toString(),
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
    }

    public void updateSchool(UpdateSchoolRequest request, Long schoolId){
        School school = schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School with ID" + schoolId + "not found!!!"));
        User rector = null;
        if(request.getRectorId() != null){
            rector = userRepository.findById(request.getRectorId()).orElseThrow(() -> new RuntimeException("User with ID" + request.getRectorId() + "not found!!!"));
        }
        SchoolMapper.updateSchoolFromDto(request,school,rector);
        schoolRepository.save(school);
    }

    public void deleteSchool(Long schoolId,User loggedUser){
        School school = schoolRepository.findById(schoolId).orElseThrow(()->new RuntimeException("School with ID" + schoolId + "not found!!!"));
        school.delete(loggedUser);
        schoolRepository.save(school);
    }
}
