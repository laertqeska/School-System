package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.schoolClass.SchoolClassCreationRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/classes")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminClassesController {
    @Autowired
    private SchoolClassService schoolClassService;
    @Autowired
    private AuthorizationService authorizationService;
    @PostMapping
    public ResponseEntity<Long> createClass(@RequestBody SchoolClassCreationRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = schoolClassService.createClass(request,loggedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
