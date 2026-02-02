package com.example.School_System.controllers.Dean;

import com.example.School_System.dto.department.CreateDepartmentRequest;
import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dean/departments")
@PreAuthorize("hasRole('DEAN')")
public class DeanDepartmentsController {
    private final DepartmentService departmentService;
    private final AuthorizationService authorizationService;

    public DeanDepartmentsController(DepartmentService departmentService, AuthorizationService authorizationService) {
        this.departmentService = departmentService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<Long> createDepartment(@RequestBody CreateDepartmentRequest request){
        User user = authorizationService.getCurrentUser();
        Long response = departmentService.createDepartment(user,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<DepartmentModelResponse>> getDepartments(){
        User user = authorizationService.getCurrentUser();
        List<DepartmentModelResponse> response = departmentService.getDepartments(user);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId){
        User user = authorizationService.getCurrentUser();
        departmentService.deleteDepartment(user,departmentId);
        return ResponseEntity.noContent().build();
    }
}
