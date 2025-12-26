package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.studyProgramSubject.CreateStudyProgramSubjectRequest;
import com.example.School_System.services.StudyProgramSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/studyProgram/{studyProgramId}/subjects")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminStudyProgramSubjectController {
    @Autowired
    private StudyProgramSubjectService studyProgramSubjectService;

    public ResponseEntity<Long> createStudyProgramSubject(@PathVariable Long studyProgramId, @RequestBody CreateStudyProgramSubjectRequest request){
            Long response = studyProgramSubjectService.createStudyProgramSubject(studyProgramId,request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
