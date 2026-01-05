package com.example.School_System.services;

import com.example.School_System.dto.academicYear.AcademicYearCreationRequest;
import com.example.School_System.entities.AcademicYear;
import com.example.School_System.entities.School;
import com.example.School_System.entities.SchoolAdmin;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.AcademicYearRepository;
import com.example.School_System.repositories.SchoolAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final SchoolAdminRepository schoolAdminRepository;

    public AcademicYearService(AcademicYearRepository academicYearRepository, SchoolAdminRepository schoolAdminRepository) {
        this.academicYearRepository = academicYearRepository;
        this.schoolAdminRepository = schoolAdminRepository;
    }

    @Transactional
    public Long createAcademicYear(User loggedUser, AcademicYearCreationRequest request){
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(loggedUser.getId()).orElseThrow(()-> new EntityNotFoundException("School admin not found for user ID: " + loggedUser.getId()));
        School school = schoolAdmin.getSchool();
        academicYearRepository.findBySchoolIdAndIsCurrentTrue(school.getId()).ifPresent(current -> current.setIsCurrent(false));
        AcademicYear academicYear = new AcademicYear(
                school,
                request.getStartDate(),
                request.getEndDate()
        );
        academicYear.setIsCurrent(true);
        AcademicYear savedAcademicYear = academicYearRepository.save(academicYear);

        return savedAcademicYear.getId();
    }
}
