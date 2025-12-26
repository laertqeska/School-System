package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.schoolClass.TeacherClassesModel;
import com.example.School_System.services.SchoolClassService;
import com.example.School_System.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolClassService schoolClassService;
    @GetMapping
    public ResponseEntity<List<TeacherClassesModel>> getClasses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<TeacherClassesModel> response = schoolClassService.getClassesForTeacher(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
