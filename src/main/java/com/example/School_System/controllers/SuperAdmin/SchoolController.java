package com.example.School_System.controllers.SuperAdmin;

import com.example.School_System.dto.school.CreateSchoolRequest;
import com.example.School_System.dto.school.PaginatedSchoolResponse;
import com.example.School_System.dto.school.SchoolDetailsResponse;
import com.example.School_System.dto.school.UpdateSchoolRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<PaginatedSchoolResponse> getAllSchools(@RequestParam int page, @RequestParam int perPage){
        PaginatedSchoolResponse response =  schoolService.listSchools(page,perPage);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createSchool(@RequestBody CreateSchoolRequest request){
        User createdBy = authorizationService.getCurrentUser();
        Long createdSchoolId = schoolService.createSchool(request,createdBy);
        return new ResponseEntity<>(createdSchoolId, HttpStatus.OK);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDetailsResponse> getSchoolDetails(@PathVariable Long schoolId){
        SchoolDetailsResponse response = schoolService.getSchoolDetails(schoolId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{schoolId}")
    public ResponseEntity<Void> updateSchool(@RequestBody UpdateSchoolRequest request,@PathVariable Long schoolId){
        schoolService.updateSchool(request,schoolId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long schoolId){
        schoolService.deleteSchool(schoolId);
        return ResponseEntity.ok().build();
    }
}
