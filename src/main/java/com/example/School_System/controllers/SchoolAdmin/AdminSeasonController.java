package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.season.CreateSeasonRequest;
import com.example.School_System.dto.season.PaginatedSeasonResponse;
import com.example.School_System.dto.season.SeasonDetailsResponse;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.SeasonStatus;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SeasonService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/seasons")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminSeasonController {
    private final SeasonService seasonService;
    private final AuthorizationService authorizationService;

    public AdminSeasonController(SeasonService seasonService, AuthorizationService authorizationService) {
        this.seasonService = seasonService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<Long> createSeason(@Valid @RequestBody CreateSeasonRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = seasonService.createSeason(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{seasonId}")
    public ResponseEntity<SeasonDetailsResponse> getSeasonDetails(@PathVariable Long seasonId){
        User loggedUser = authorizationService.getCurrentUser();
        SeasonDetailsResponse response = seasonService.getSeasonDetails(loggedUser,seasonId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PaginatedSeasonResponse> getSeasonsByFaculty(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int perPage,@RequestParam Long facultyId,@RequestParam(required = false) SeasonStatus status){
        PaginatedSeasonResponse response = seasonService.getSeasonsByFaculty(facultyId,status,page,perPage);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
