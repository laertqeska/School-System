package com.example.School_System.controllers.Dean;


import com.example.School_System.dto.studyProgram.CreateStudyProgramRequest;
import com.example.School_System.dto.studyProgram.PaginatedStudyProgramResponse;
import com.example.School_System.dto.studyProgramSubject.CreateStudyProgramSubjectRequest;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.DegreeLevel;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudyProgramService;
import com.example.School_System.services.StudyProgramSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dean/study-programs")
@PreAuthorize("hasRole('DEAN')")
public class DeanStudyProgramController {

    private final StudyProgramService studyProgramService;
    private final StudyProgramSubjectService studyProgramSubjectService;
    private AuthorizationService authorizationService;

    public DeanStudyProgramController(StudyProgramService studyProgramService, StudyProgramSubjectService studyProgramSubjectService, AuthorizationService authorizationService) {
        this.studyProgramService = studyProgramService;
        this.studyProgramSubjectService = studyProgramSubjectService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedStudyProgramResponse> getStudyPrograms(@RequestParam int page, @RequestParam int perPage,@RequestParam(required = false) DegreeLevel degreeLevel,@RequestParam(required = false) String studyProgramName){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedStudyProgramResponse response = studyProgramService.getStudyProgramsForFaculty(page,perPage,studyProgramName,degreeLevel,loggedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createStudyProgram(@RequestBody CreateStudyProgramRequest request){
        User user = authorizationService.getCurrentUser();
        Long response = studyProgramService.createStudyProgram(request,user);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("{studyProgramSubjectId}/subject")
    public ResponseEntity<Long> addSubjectToProgram(@PathVariable Long studyProgramSubjectId,@RequestBody CreateStudyProgramSubjectRequest request){
        User dean = authorizationService.getCurrentUser();
        Long id = studyProgramSubjectService.createStudyProgramSubject(studyProgramSubjectId,request,dean);
        return new ResponseEntity<>(id,HttpStatus.CREATED);
    }


}