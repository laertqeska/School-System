package com.example.School_System.controllers;

import com.example.School_System.dto.school.CreateSchoolRequest;
import com.example.School_System.dto.school.PaginatedSchoolResponse;
import com.example.School_System.dto.school.SchoolDetailsResponse;
import com.example.School_System.dto.school.UpdateSchoolRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public ResponseEntity<PaginatedSchoolResponse> getAllSchools(@RequestParam int page, @RequestParam int perPage){
        PaginatedSchoolResponse response =  schoolService.listSchools(page,perPage);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createSchool(@RequestBody CreateSchoolRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User createdBy = (User) authentication.getPrincipal();
        Long createdSchoolId = schoolService.createSchool(request,createdBy);
        return new ResponseEntity<>(createdSchoolId, HttpStatus.OK);
    }

    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDetailsResponse> getSchoolDetails(@PathVariable Long schoolId){
        SchoolDetailsResponse response = schoolService.getSchoolDetails(schoolId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{schoolId}")
    public ResponseEntity<HttpStatus> updateSchool(@RequestBody UpdateSchoolRequest request,@PathVariable Long schoolId){
        schoolService.updateSchool(request,schoolId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{schoolId}")
    public ResponseEntity<HttpStatus> deleteSchool(@PathVariable Long schoolId){
        schoolService.deleteSchool(schoolId);
        return ResponseEntity.ok().build();
    }
}
