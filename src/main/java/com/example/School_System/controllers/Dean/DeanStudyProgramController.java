package com.example.School_System.controllers.Dean;


import com.example.School_System.dto.studyProgram.PaginatedStudyProgramResponse;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.DegreeLevel;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dean/study-programs")
@PreAuthorize("hasRole('DEAN')")
public class DeanStudyProgramController {
    @Autowired
    private StudyProgramService studyProgramService;
    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<PaginatedStudyProgramResponse> getStudyPrograms(@RequestParam int page, @RequestParam int perPage,@RequestParam(required = false) DegreeLevel degreeLevel,@RequestParam(required = false) String studyProgramName){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedStudyProgramResponse response = studyProgramService.getStudyProgramsForFaculty(page,perPage,studyProgramName,degreeLevel,loggedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}