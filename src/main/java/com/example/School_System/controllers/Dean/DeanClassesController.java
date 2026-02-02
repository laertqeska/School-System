package com.example.School_System.controllers.Dean;

import com.example.School_System.dto.schoolClass.SchoolClassCreationRequest;
import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dean/classes")
@PreAuthorize("hasRole('DEAN')")
public class DeanClassesController {
    private final SchoolClassService schoolClassService;
    private final AuthorizationService authorizationService;

    public DeanClassesController(SchoolClassService schoolClassService,AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
        this.schoolClassService = schoolClassService;
    }
    @PostMapping
    public ResponseEntity<Long> createClass(@RequestBody SchoolClassCreationRequest request){
        User dean = authorizationService.getCurrentUser();
        Long response = schoolClassService.createClass(request,dean);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
