package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.studyProgram.CreateStudyProgramRequest;
import com.example.School_System.dto.studyProgram.PaginatedStudyProgramResponse;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.DegreeLevel;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/departments/{departmentId}/study-programs")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminStudyProgramController {
    @Autowired
    private StudyProgramService studyProgramService;
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<Long> createStudyProgram(@PathVariable Long departmentId,@RequestBody CreateStudyProgramRequest request){
        Long response = studyProgramService.createStudyProgram(departmentId,request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedStudyProgramResponse> getStudyPrograms(@PathVariable Long departmentId, @RequestParam int page, @RequestParam int perPage, @RequestParam(required = false) String studyProgramName, @RequestParam(required = false) DegreeLevel degreeLevel){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedStudyProgramResponse response =  studyProgramService.getStudyProgramsForSchool(page,perPage,studyProgramName,degreeLevel,loggedUser,departmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
