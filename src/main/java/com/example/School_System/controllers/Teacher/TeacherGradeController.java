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
    @Autowired
    private GradeService gradeService;
    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<PaginatedTeachersGradeResponse> getGrades(
            @RequestParam Long studyProgramSubjectId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) BigDecimal score,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        PaginatedTeachersGradeResponse response = gradeService.getGradesForTeacher(studyProgramSubjectId,classId,page,perPage,search,score);
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
        gradeService.updateGrade(gradeId,score);
        return ResponseEntity.noContent().build();
    }

}
