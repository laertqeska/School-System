package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.subject.CreateSubjectRequest;
import com.example.School_System.dto.subject.SchoolAdminSubjectModel;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments/{departmentId}/subjects")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminSubjectController {
    private final SubjectService subjectService;
    private final AuthorizationService authorizationService;

    public AdminSubjectController(SubjectService subjectService, AuthorizationService authorizationService) {
        this.subjectService = subjectService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<List<SchoolAdminSubjectModel>> getSubjectsForDepartment(@PathVariable Long departmentId){
        User loggedUser = authorizationService.getCurrentUser();
        List<SchoolAdminSubjectModel> response = subjectService.getSubjectsForDepartment(loggedUser,departmentId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
