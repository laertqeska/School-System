package com.example.School_System.controllers;

import com.example.School_System.dto.authentication.AuthenticationRequest;
import com.example.School_System.dto.authentication.AuthenticationResponse;
import com.example.School_System.dto.authentication.RegisterRequest;
import com.example.School_System.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    //This endpoint is currently for testing purposes
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.registerSuperAdmin(request));
    }

    @PostMapping("/login/student")
    public ResponseEntity<AuthenticationResponse> authenticateStudent(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateStudent(request));
    }

    @PostMapping("/login/teacher")
    public ResponseEntity<AuthenticationResponse> authenticateTeacher(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateTeacher(request));
    }

    @PostMapping("/login/school-admin")
    public ResponseEntity<AuthenticationResponse> authenticateSchoolAdmin(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateSchoolAdmin(request));
    }

    @PostMapping("/login/super-admin")
    public ResponseEntity<AuthenticationResponse> authenticateSuperAdmin(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticateSuperAdmin(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
