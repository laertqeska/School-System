package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.faculty.CreateFacultyRequest;
import com.example.School_System.dto.faculty.PaginatedFacultyResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/faculties")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminFacultyController {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private FacultyService facultyService;

    @PostMapping
    public ResponseEntity<Long> createFaculty(@RequestBody CreateFacultyRequest request){
        User user = authorizationService.getCurrentUser();
        Long response = facultyService.createFaculty(request,user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedFacultyResponse> getAllFaculties(@RequestParam int page,@RequestParam int perPage, @RequestParam(required = false) String search){
        User user = authorizationService.getCurrentUser();
        PaginatedFacultyResponse response = facultyService.getAllFaculties(user,search,page,perPage);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
