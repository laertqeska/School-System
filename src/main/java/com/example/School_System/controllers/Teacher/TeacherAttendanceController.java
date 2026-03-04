package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.attendance.TakeAttendanceRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AttendanceService;
import com.example.School_System.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/attendance")
public class TeacherAttendanceController {
    private final AuthorizationService authorizationService;
    private final AttendanceService attendanceService;

    public TeacherAttendanceController(AuthorizationService authorizationService, AttendanceService attendanceService) {
        this.authorizationService = authorizationService;
        this.attendanceService = attendanceService;
    }


    @PostMapping
    public ResponseEntity<HttpStatus> takeAttendance(@Valid @RequestBody TakeAttendanceRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        attendanceService.takeAttendance(loggedUser,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
