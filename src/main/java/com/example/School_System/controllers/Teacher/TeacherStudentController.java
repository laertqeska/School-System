package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher/students")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherStudentController {
    private final StudentService studentService;
    private final AuthorizationService authorizationService;

    public TeacherStudentController(StudentService studentService, AuthorizationService authorizationService) {
        this.studentService = studentService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDetailsResponse> getStudent(@PathVariable Long studentId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorizationService.validateTeacherAccessToStudent(auth,studentId);
        StudentDetailsResponse response = studentService.getStudentDetails(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
