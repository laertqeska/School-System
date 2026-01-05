package com.example.School_System.controllers.SuperAdmin;

import com.example.School_System.dto.rector.PaginatedRectorResponse;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.RectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/super-admin/rectors")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class RectorController {
    private final RectorService rectorService;
    private final AuthorizationService authorizationService;

    public RectorController(RectorService rectorService, AuthorizationService authorizationService) {
        this.rectorService = rectorService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<PaginatedRectorResponse> getRectors(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int perPage,@RequestParam(required = false) String search){
        User loggedUser = authorizationService.getCurrentUser();
        PaginatedRectorResponse response = rectorService.getRectors(page,perPage,loggedUser,search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
