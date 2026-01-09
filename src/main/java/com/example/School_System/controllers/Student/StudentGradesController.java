package com.example.School_System.controllers.Student;

import com.example.School_System.dto.grade.PaginatedStudentsGradeResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/grades")
@PreAuthorize("hasRole('STUDENT')")
public class StudentGradesController {
    private final GradeService gradeService;
    private final AuthorizationService authorizationService;

    public StudentGradesController(GradeService gradeService, AuthorizationService authorizationService) {
        this.gradeService = gradeService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedStudentsGradeResponse> getGrades(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) Integer year
    ){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedStudentsGradeResponse response =  gradeService.getGradesForStudent(loggedUser, semester,year,page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
