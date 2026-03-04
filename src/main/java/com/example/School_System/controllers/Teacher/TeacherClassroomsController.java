package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.classroom.PaginatedAvailableClassroomsResponse;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.ClassroomType;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/teacher/classrooms")
public class TeacherClassroomsController {

    private final AuthorizationService authorizationService;
    private final ClassroomService classroomService;

    public TeacherClassroomsController(AuthorizationService authorizationService, ClassroomService classroomService) {
        this.authorizationService = authorizationService;
        this.classroomService = classroomService;
    }


    @GetMapping
    public ResponseEntity<PaginatedAvailableClassroomsResponse> getPaginatedAvailableClassrooms(@RequestParam LocalTime startTime,
                                                                                                @RequestParam LocalTime endTime,
                                                                                                @RequestParam(defaultValue = "1") int page,
                                                                                                @RequestParam(defaultValue = "10") int perPage,
                                                                                                @RequestParam(required = false) Long buildingId,
                                                                                                @RequestParam(required = false) String search,
                                                                                                @RequestParam(required = false) Boolean isAccessible,
                                                                                                @RequestParam(required = false) Boolean hasProjector,
                                                                                                @RequestParam(required = false)ClassroomType classroomType,
                                                                                                @RequestParam(required = false) LocalDate date)
    {
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedAvailableClassroomsResponse response = classroomService.getAvailableClassroomsInFacultyForTeacher(loggedUser,buildingId,isAccessible,hasProjector,search,classroomType,startTime,endTime,date,page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
