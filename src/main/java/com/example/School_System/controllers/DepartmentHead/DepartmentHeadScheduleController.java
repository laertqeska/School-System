package com.example.School_System.controllers.DepartmentHead;

import com.example.School_System.dto.schedule.CreateScheduleRequest;
import com.example.School_System.dto.schedule.ScheduleForClassResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department-head/schedules")
@PreAuthorize("hasRole('DEPARTMENT_HEAD')")
public class DepartmentHeadScheduleController {
    private final AuthorizationService authorizationService;
    private final ScheduleService scheduleService;

    public DepartmentHeadScheduleController(AuthorizationService authorizationService, ScheduleService scheduleService) {
        this.authorizationService = authorizationService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Long> createSchedule(@Valid @RequestBody CreateScheduleRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = scheduleService.createSchedule(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/classes/{classId}")
    public ResponseEntity<ScheduleForClassResponse> getScheduleForClass(@PathVariable Long classId){
        User loggedUser = authorizationService.getCurrentUser();
        ScheduleForClassResponse response = scheduleService.getScheduleForClass(loggedUser,classId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
