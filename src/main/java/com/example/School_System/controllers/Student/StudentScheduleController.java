package com.example.School_System.controllers.Student;

import com.example.School_System.entities.User;
import com.example.School_System.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/schedule")
public class StudentScheduleController {
//    @Autowired
//    private StudentService studentService;
//
//    @GetMapping
//    private ResponseEntity<?> getSchedule(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User loggedUser = (User) auth.getPrincipal();
//
//    }
}
