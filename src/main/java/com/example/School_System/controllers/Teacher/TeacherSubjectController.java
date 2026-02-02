package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.studyProgramSubject.TeacherStudyProgramSubjectModel;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.StudyProgramSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/subjects")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherSubjectController {
    private final StudyProgramSubjectService studyProgramSubjectService;
    private final AuthorizationService authorizationService;

    public TeacherSubjectController(StudyProgramSubjectService studyProgramSubjectService, AuthorizationService authorizationService) {
        this.studyProgramSubjectService = studyProgramSubjectService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherStudyProgramSubjectModel>> getStudyProgramSubjects(){
        User user = authorizationService.getCurrentUser();
        List<TeacherStudyProgramSubjectModel> response =  studyProgramSubjectService.getStudyProgramSubjectsForTeacher(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
