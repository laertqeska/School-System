package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.teaching.AssignTeacherToClassAndSubjectRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.TeacherAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/teaching-assignments")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminTeachingController {
    private TeacherAssignmentService teacherAssignmentService;
    private AuthorizationService authorizationService;

    public AdminTeachingController(TeacherAssignmentService teacherAssignmentService,AuthorizationService authorizationService) {
        this.teacherAssignmentService = teacherAssignmentService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<Void> assignTeacherToSubjectAndClass(@RequestBody AssignTeacherToClassAndSubjectRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        teacherAssignmentService.assignTeacherToSubjectAndClass(request,loggedUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
