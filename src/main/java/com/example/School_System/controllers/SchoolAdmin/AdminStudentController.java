package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.student.CreateStudentRequest;
import com.example.School_System.dto.student.PaginatedStudentResponse;
import com.example.School_System.dto.student.StudentDetailsResponse;
import com.example.School_System.dto.student.UpdateStudentRequest;
import com.example.School_System.entities.User;
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
    private final StudentService studentService;
    private final StudentEnrollmentService studentEnrollmentService;
    private final AuthorizationService authorizationService;

    public AdminStudentController(StudentService studentService, StudentEnrollmentService studentEnrollmentService, AuthorizationService authorizationService) {
        this.studentService = studentService;
        this.studentEnrollmentService = studentEnrollmentService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedStudentResponse> getStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue="10") int perPage){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedStudentResponse response =  studentService.listAllStudentsForSchoolAdmin(loggedUser,page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createStudent(@RequestBody CreateStudentRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long createdStudentId = studentEnrollmentService.createStudent(request,loggedUser);
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
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        studentService.updateStudent(loggedUser,request,id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        User loggedUser = authorizationService.getCurrentUser();
        studentService.deleteStudent(id,loggedUser);
        return ResponseEntity.ok().build();
    }
}
