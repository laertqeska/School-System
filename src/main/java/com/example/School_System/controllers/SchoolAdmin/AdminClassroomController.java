package com.example.School_System.controllers.SchoolAdmin;


import com.example.School_System.dto.classroom.ClassroomDetailsResponse;
import com.example.School_System.dto.classroom.CreateClassroomRequest;
import com.example.School_System.dto.classroom.PaginatedClassroomsForBuildingResponse;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.ClassroomType;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/classrooms")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminClassroomController {
    private final AuthorizationService authorizationService;
    private final ClassroomService classroomService;

    public AdminClassroomController(AuthorizationService authorizationService, ClassroomService classroomService){
        this.authorizationService = authorizationService;
        this.classroomService = classroomService;
    }

    @PostMapping
    public ResponseEntity<Long> createClassroom(@Valid @RequestBody CreateClassroomRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = classroomService.createClassroom(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/buildings/{buildingId}")
    public ResponseEntity<PaginatedClassroomsForBuildingResponse> getClassroomsForBuilding(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ClassroomType classroomType,
            @PathVariable Long buildingId){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedClassroomsForBuildingResponse response = classroomService.getClassroomsForBuilding(loggedUser,buildingId,page,perPage,search,classroomType);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomDetailsResponse> getClassroomDetails(@PathVariable Long classroomId){
        User loggedUser = authorizationService.getCurrentUser();
        ClassroomDetailsResponse response = classroomService.getClassroomDetails(loggedUser,classroomId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long classroomId){
        User loggedUser = authorizationService.getCurrentUser();
        classroomService.deleteClassroom(loggedUser,classroomId);
        return ResponseEntity.noContent().build();
    }
}
