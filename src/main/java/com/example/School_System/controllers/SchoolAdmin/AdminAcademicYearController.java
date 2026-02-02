package com.example.School_System.controllers.SchoolAdmin;


import com.example.School_System.dto.academicYear.AcademicYearCreationRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AcademicYearService;
import com.example.School_System.services.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/academic-year")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminAcademicYearController {
    private final AcademicYearService academicYearService;
    private final AuthorizationService authorizationService;

    public AdminAcademicYearController(AcademicYearService academicYearService,AuthorizationService authorizationService) {
        this.academicYearService = academicYearService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<Long> createAcademicYear(@RequestBody AcademicYearCreationRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long id = academicYearService.createAcademicYear(loggedUser,request);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{academicYearId}")
    public ResponseEntity<Void> activateAcademicYear(@PathVariable Long academicYearId){
        User loggedUser = authorizationService.getCurrentUser();
        academicYearService.activateAcademicYear(loggedUser,academicYearId);
        return ResponseEntity.noContent().build();
    }
}
