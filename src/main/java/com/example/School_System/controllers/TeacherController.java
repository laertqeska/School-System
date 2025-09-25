package com.example.School_System.controllers;


import com.example.School_System.dto.teacher.CreateTeacherRequest;
import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.dto.teacher.TeacherDetailsResponse;
import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.services.TeacherEnrollmentService;
import com.example.School_System.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools/{schoolId}/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherEnrollmentService teacherCreationService;


    @PostMapping("/create")
    private ResponseEntity<Long> createTeacher(@PathVariable Long schoolId, @RequestBody CreateTeacherRequest request){
        Long id = teacherCreationService.createTeacher(request,schoolId);
        return new ResponseEntity<>(id,HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<PaginatedTeacherResponse> getTeachers(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int perPage,@RequestParam(required = false) String search,@PathVariable Long schoolId){
        PaginatedTeacherResponse response =  teacherService.listTeachers(schoolId,page,perPage,search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    private ResponseEntity<TeacherDetailsResponse> getTeacherDetails(@PathVariable Long teacherId,@PathVariable Long schoolId){
      TeacherDetailsResponse response = teacherService.getTeacherDetails(teacherId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{teacherId}")
    private ResponseEntity<HttpStatus> updateTeacher(@PathVariable Long teacherId, @PathVariable Long schoolId, @RequestBody UpdateTeacherRequest request){
        teacherService.updateTeacher(request,teacherId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teacherId}")
    private ResponseEntity<HttpStatus> deleteTeacher(@PathVariable Long teacherId,@PathVariable Long schoolId){
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok().build();
    }
}
