package com.example.School_System.controllers.SchoolAdmin;


import com.example.School_System.dto.schedule.ScheduleForTeacherResponse;
import com.example.School_System.dto.teacher.CreateTeacherRequest;
import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.dto.teacher.TeacherDetailsResponse;
import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.ScheduleService;
import com.example.School_System.services.TeacherEnrollmentService;
import com.example.School_System.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/teachers")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminTeacherController {
    private final TeacherService teacherService;
    private final TeacherEnrollmentService teacherCreationService;
    private final AuthorizationService authorizationService;
    private final ScheduleService scheduleService;

    public AdminTeacherController(TeacherService teacherService, TeacherEnrollmentService teacherCreationService, AuthorizationService authorizationService, ScheduleService scheduleService) {
        this.teacherService = teacherService;
        this.teacherCreationService = teacherCreationService;
        this.authorizationService = authorizationService;
        this.scheduleService = scheduleService;
    }


    @PostMapping("/create")
    public ResponseEntity<Long> createTeacher(@RequestBody CreateTeacherRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long id = teacherCreationService.createTeacher(request,loggedUser);
        return new ResponseEntity<>(id,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedTeacherResponse> getTeachers(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int perPage,@RequestParam(required = false) String search){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedTeacherResponse response =  teacherService.listTeachers(loggedUser,page,perPage,search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDetailsResponse> getTeacherDetails(@PathVariable Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authorizationService.validateAdminAccessToTeacher(authentication,teacherId);
        TeacherDetailsResponse response = teacherService.getTeacherDetails(teacherId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Void> updateTeacher(@PathVariable Long teacherId, @RequestBody UpdateTeacherRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authorizationService.validateAdminAccessToTeacher(authentication,teacherId);
        teacherService.updateTeacher(request,teacherId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authorizationService.validateAdminAccessToTeacher(authentication,teacherId);
        User loggedUser = authorizationService.getCurrentUser();
        teacherService.deleteTeacher(teacherId,loggedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{teacherId}/schedule")
    public ResponseEntity<ScheduleForTeacherResponse> getTeacherSchedule(@PathVariable Long teacherId){
        User loggedUser = authorizationService.getCurrentUser();
        ScheduleForTeacherResponse response = scheduleService.getTeacherScheduleForAdmin(loggedUser,teacherId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
