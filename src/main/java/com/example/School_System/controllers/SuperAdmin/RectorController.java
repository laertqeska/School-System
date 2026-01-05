package com.example.School_System.controllers.SuperAdmin;

import com.example.School_System.services.RectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/super-admin/rectors")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class RectorController {
    private final RectorService rectorService;

    public RectorController(RectorService rectorService) {
        this.rectorService = rectorService;
    }

    @GetMapping
    public ResponseEntity<?> getRectors(){

    }

}
