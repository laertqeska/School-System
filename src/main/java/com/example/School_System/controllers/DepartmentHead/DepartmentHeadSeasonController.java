package com.example.School_System.controllers.DepartmentHead;

import com.example.School_System.dto.season.PaginatedSeasonResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.SeasonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/department-head/seasons")
@PreAuthorize("hasRole('DEPARTMENT_HEAD')")
public class DepartmentHeadSeasonController {
    private final AuthorizationService authorizationService;
    private final SeasonService seasonService;

    public DepartmentHeadSeasonController(AuthorizationService authorizationService, SeasonService seasonService) {
        this.authorizationService = authorizationService;
        this.seasonService = seasonService;
    }

    @GetMapping
    public ResponseEntity<PaginatedSeasonResponse> getPaginatedSeasons(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int perPage){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedSeasonResponse response = seasonService.getSeasonsByDepartment(page,perPage,loggedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
