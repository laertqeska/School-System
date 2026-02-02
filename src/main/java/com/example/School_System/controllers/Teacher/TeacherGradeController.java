package com.example.School_System.controllers.Teacher;

import com.example.School_System.dto.grade.CreateGradeRequest;
import com.example.School_System.dto.grade.PaginatedTeachersGradeResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/teacher/grades")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherGradeController {
    private final GradeService gradeService;
    private final AuthorizationService authorizationService;

    public TeacherGradeController(GradeService gradeService, AuthorizationService authorizationService) {
        this.gradeService = gradeService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedTeachersGradeResponse> getGrades(
            @RequestParam Long studyProgramSubjectId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) BigDecimal score,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedTeachersGradeResponse response = gradeService.getGradesForTeacher(loggedUser,studyProgramSubjectId,classId,page,perPage,search,score);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createGrade(@RequestBody CreateGradeRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = gradeService.createGrade(request,loggedUser);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{gradeId}")
    public ResponseEntity<Void> updateGradeScore(@PathVariable Long gradeId,@RequestBody BigDecimal score){
        User loggedUser = authorizationService.getCurrentUser();
        gradeService.updateGrade(gradeId,score,loggedUser);
        return ResponseEntity.noContent().build();
    }

}
