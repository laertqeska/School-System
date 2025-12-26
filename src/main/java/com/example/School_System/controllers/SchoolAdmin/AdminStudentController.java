package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.student.CreateStudentRequest;
import com.example.School_System.dto.student.PaginatedStudentResponse;
import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudentEnrollmentService;
import com.example.School_System.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/students")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminStudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<PaginatedStudentResponse> getStudents(@RequestParam int page, @RequestParam int perPage){
        PaginatedStudentResponse response =  studentService.listAllStudents(page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createStudent(@RequestBody CreateStudentRequest request){
        Long createdStudentId = studentEnrollmentService.createStudent(request);
        return new ResponseEntity<>(createdStudentId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsResponse> getStudentDetails(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorizationService.validateAdminAccessToStudent(auth,id);
        StudentDetailsResponse response =  studentService.getStudentDetails(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSchool(@PathVariable Long id, @RequestBody UpdateStudentRequest request){
        studentService.updateStudent(request,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
