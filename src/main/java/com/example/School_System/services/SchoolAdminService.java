package com.example.School_System.services;

import com.example.School_System.dto.schoolAdmin.PaginatedSchoolAdminResponse;
import com.example.School_System.dto.schoolAdmin.SchoolAdminDetailsResponse;
import com.example.School_System.dto.schoolAdmin.SchoolAdminModel;
import com.example.School_System.entities.SchoolAdmin;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.SchoolAdminRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SchoolAdminService {
    private final SchoolAdminRepository schoolAdminRepository;
    private final UserRepository userRepository;

    public SchoolAdminService(SchoolAdminRepository schoolAdminRepository, UserRepository userRepository) {
        this.schoolAdminRepository = schoolAdminRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public void deleteAdmin(Long schoolAdminId,User loggedUser){
        SchoolAdmin schoolAdmin = schoolAdminRepository.findById(schoolAdminId).orElseThrow(()-> new EntityNotFoundException("School admin not found with ID: " + schoolAdminId));
        if (schoolAdmin.getDeleted()) {
            throw new IllegalStateException("Teacher already deleted");
        }
        schoolAdmin.delete(loggedUser);
        schoolAdminRepository.save(schoolAdmin);
        User user = schoolAdmin.getUser();
        user.delete(loggedUser);
        userRepository.save(loggedUser);
    }

}
