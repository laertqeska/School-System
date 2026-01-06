package com.example.School_System.services;

import com.example.School_System.dto.schoolAdmin.PaginatedSchoolAdminResponse;
import com.example.School_System.dto.schoolAdmin.SchoolAdminDetailsResponse;
import com.example.School_System.dto.schoolAdmin.SchoolAdminModel;
import com.example.School_System.entities.SchoolAdmin;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.SchoolAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SchoolAdminService {
    private final SchoolAdminRepository schoolAdminRepository;

    public SchoolAdminService(SchoolAdminRepository schoolAdminRepository) {
        this.schoolAdminRepository = schoolAdminRepository;
    }

    public PaginatedSchoolAdminResponse getAllSchoolAdmin(User loggedUser,int page,int perPage,String search){
        Pageable pageable = PageRequest.of(page,perPage);
        if(search == null || search.isBlank()){
            search = "";
        }
        else{
            search = search.toLowerCase();
        }
        Page<SchoolAdminModel> schoolAdminsPage =  schoolAdminRepository.findSchoolAdminsWithSearch(pageable,search);
        return new PaginatedSchoolAdminResponse(
                schoolAdminsPage.getContent(),
                page,
                perPage,
                schoolAdminsPage.getTotalElements(),
                schoolAdminsPage.getTotalPages()
        );
    }

    public SchoolAdminDetailsResponse getAdminDetails(Long schoolAdminId){
        SchoolAdmin schoolAdmin =  schoolAdminRepository.findById(schoolAdminId).orElseThrow(()->new EntityNotFoundException("School admin not found with ID: " + schoolAdminId));
        User user = schoolAdmin.getUser();
        return new SchoolAdminDetailsResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                schoolAdmin.getSchool().getName()
        );
    }

    public void deleteAdmin(Long schoolAdminId){
        SchoolAdmin schoolAdmin = schoolAdminRepository.findById(schoolAdminId).orElseThrow(()-> new EntityNotFoundException("School admin not found with ID: " + schoolAdminId));
        schoolAdminRepository.delete(schoolAdmin);
    }

}
