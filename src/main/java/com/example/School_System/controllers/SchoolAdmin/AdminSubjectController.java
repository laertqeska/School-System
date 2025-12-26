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
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<Long> createSubject(@PathVariable Long departmentId,@RequestBody CreateSubjectRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = subjectService.createSubject(loggedUser,request,departmentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchoolAdminSubjectModel>> getSubjectsForDepartment(@PathVariable Long departmentId){
        List<SchoolAdminSubjectModel> response = subjectService.getSubjectsForDepartment(departmentId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
