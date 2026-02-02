package com.example.School_System.controllers.Student;

import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/profile")
@PreAuthorize("hasRole('STUDENT')")
public class StudentProfileController {
    private final StudentService studentService;
    private final AuthorizationService authorizationService;

    public StudentProfileController(StudentService studentService, AuthorizationService authorizationService) {
        this.studentService = studentService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<StudentDetailsResponse> getStudentProfile(){
        User loggedUser = authorizationService.getCurrentUser();
        StudentDetailsResponse response = studentService.getStudentDetailsForStudent(loggedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}