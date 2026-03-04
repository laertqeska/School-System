package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.schedule.ScheduleForTeacherResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher/schedule")
public class TeacherScheduleController {
    private final AuthorizationService authorizationService;
    private final ScheduleService scheduleService;

    public TeacherScheduleController(AuthorizationService authorizationService, ScheduleService scheduleService) {
        this.authorizationService = authorizationService;
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<ScheduleForTeacherResponse> getSchedule(){
        User loggedUser = authorizationService.getCurrentUser();
        ScheduleForTeacherResponse response =  scheduleService.getTeacherScheduleForTeacher(loggedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
