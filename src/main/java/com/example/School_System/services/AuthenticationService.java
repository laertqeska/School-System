package com.example.School_System.services;

import com.example.School_System.dto.authentication.AuthenticationRequest;
import com.example.School_System.dto.authentication.AuthenticationResponse;
import com.example.School_System.dto.authentication.RegisterRequest;
import com.example.School_System.entities.Role;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.RoleName;
import com.example.School_System.repositories.RoleRepository;
import com.example.School_System.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse registerSuperAdmin(RegisterRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists!");
        }
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists!");
        }
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName()
        );
        Role role = roleRepository.findByName(RoleName.SUPER_ADMIN).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticateWithRole(AuthenticationRequest request, RoleName requiredRole){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        boolean hasRequiredRole = user.getRoles().stream().anyMatch(role -> role.getName() == requiredRole);
        if(!hasRequiredRole){
            throw new RuntimeException("Access denied: " + requiredRole + " role required!!!");
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    public AuthenticationResponse authenticateSuperAdmin(AuthenticationRequest request) {
        return authenticateWithRole(request,RoleName.SUPER_ADMIN);
    }

    public AuthenticationResponse authenticateStudent(AuthenticationRequest request) {
        return authenticateWithRole(request,RoleName.STUDENT);
    }

    public AuthenticationResponse authenticateTeacher(AuthenticationRequest request) {
        return authenticateWithRole(request,RoleName.TEACHER);
    }

    public AuthenticationResponse authenticateSchoolAdmin(AuthenticationRequest request) {
        return authenticateWithRole(request,RoleName.SCHOOL_ADMIN);
    }

    public AuthenticationResponse authenticateRector(AuthenticationRequest request){
        return authenticateWithRole(request,RoleName.RECTOR);
    }

    public AuthenticationResponse authenticateDean(AuthenticationRequest request){
        return authenticateWithRole(request,RoleName.DEAN);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid refresh token!");
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail != null){
            var user = userRepository.findByUsername(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
                var accessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
            }
        }
        throw new IllegalArgumentException("Invalid refresh token!");
    }
}
