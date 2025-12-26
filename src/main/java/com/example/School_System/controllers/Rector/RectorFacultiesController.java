package com.example.School_System.controllers.Rector;

import com.example.School_System.dto.rector.PaginatedRectorFacultiesResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.RectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rector/faculties")
public class RectorFacultiesController {
    private RectorService rectorService;
    private AuthorizationService authorizationService;

    public RectorFacultiesController(RectorService rectorService,AuthorizationService authorizationService){
        this.rectorService = rectorService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedRectorFacultiesResponse> getFacultiesForRector(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int perPage){
        User user = authorizationService.getCurrentUser();
        PaginatedRectorFacultiesResponse response = rectorService.getFacultiesForRector(user,page,perPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<HttpStatus> approveFaculty(@RequestParam String token){
        User user = authorizationService.getCurrentUser();
        rectorService.approveFaculty(token,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectFaculty(@RequestParam String token,@RequestBody String rejectionReason){
        User user = authorizationService.getCurrentUser();
        rectorService.rejectFaculty(token,user,rejectionReason);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
