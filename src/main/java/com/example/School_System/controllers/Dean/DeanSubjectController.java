package com.example.School_System.controllers.Dean;


import com.example.School_System.dto.subject.CreateSubjectRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dean/subjects")
@PreAuthorize("hasRole('DEAN')")
public class DeanSubjectController {
    private AuthorizationService authorizationService;
    private final SubjectService subjectService;

    public DeanSubjectController(SubjectService subjectService,AuthorizationService authorizationService) {
        this.subjectService = subjectService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<Long> createSubject(@RequestBody CreateSubjectRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = subjectService.createSubject(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
