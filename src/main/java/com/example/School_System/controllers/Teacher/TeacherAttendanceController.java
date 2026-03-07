package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.attendance.PaginatedClassAttendanceResponse;
import com.example.School_System.dto.attendance.TakeAttendanceRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AttendanceService;
import com.example.School_System.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher/attendance")
@PreAuthorize("hasRole('TEACHER')")
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

    @GetMapping("/classes/{classId}")
    public ResponseEntity<?> getAttendanceForClass(@PathVariable Long classId,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int perPage){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedClassAttendanceResponse response = attendanceService.getAttendanceForClass(loggedUser,classId,page,perPage);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
