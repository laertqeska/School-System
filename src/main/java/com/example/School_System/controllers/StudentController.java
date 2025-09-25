package com.example.School_System.controllers;

import com.example.School_System.dto.student.CreateStudentRequest;
import com.example.School_System.dto.student.PaginatedStudentResponse;
import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.services.StudentEnrollmentService;
import com.example.School_System.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    @GetMapping
    private ResponseEntity<PaginatedStudentResponse> getStudents(@RequestParam int page,@RequestParam int perPage){
        PaginatedStudentResponse response =  studentService.listStudents(page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody CreateStudentRequest request){
        Long createdStudentId = studentEnrollmentService.createStudent(request);
        return new ResponseEntity<>(createdStudentId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsResponse> getStudentDetails(@PathVariable Long id){
        StudentDetailsResponse response =  studentService.getStudentDetails(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateSchool(@PathVariable Long id, @RequestBody UpdateStudentRequest request){
        studentService.updateStudent(request,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSchool(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
