package com.example.School_System.controllers.Dean;

import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dean/teachers")
@PreAuthorize("hasRole('DEAN')")
public class DeanTeacherController {
    private final TeacherService teacherService;
    private final AuthorizationService authorizationService;

    public DeanTeacherController(TeacherService teacherService, AuthorizationService authorizationService) {
        this.teacherService = teacherService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedTeacherResponse> getTeachers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int perPage, @RequestParam(required = false) String search){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedTeacherResponse response =  teacherService.getTeachersByFaculty(loggedUser,page,perPage,search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
