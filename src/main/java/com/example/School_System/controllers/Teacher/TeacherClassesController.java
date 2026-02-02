package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.schoolClass.TeacherClassesModel;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SchoolClassService;
import com.example.School_System.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/classes")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherClassesController {
    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;
    private final AuthorizationService authorizationService;

    public TeacherClassesController(TeacherService teacherService, SchoolClassService schoolClassService, AuthorizationService authorizationService) {
        this.teacherService = teacherService;
        this.schoolClassService = schoolClassService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherClassesModel>> getClasses(){
        User loggedUser = authorizationService.getCurrentUser();
        List<TeacherClassesModel> response = schoolClassService.getClassesForTeacher(loggedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
