package com.example.School_System.controllers.SchoolAdmin;

import com.example.School_System.dto.building.BuildingDetailsResponse;
import com.example.School_System.dto.building.BuildingModel;
import com.example.School_System.dto.building.CreateBuildingRequest;
import com.example.School_System.entities.User;
import com.example.School_System.services.AuthorizationService;
import com.example.School_System.services.BuildingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buildings")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class AdminBuildingController {
    private final AuthorizationService authorizationService;
    private final BuildingService buildingService;

    public AdminBuildingController(AuthorizationService authorizationService, BuildingService buildingService) {
        this.authorizationService = authorizationService;
        this.buildingService = buildingService;
    }

    @PostMapping
    public ResponseEntity<Long> createBuilding(@Valid @RequestBody CreateBuildingRequest request){
        User loggedUser = authorizationService.getCurrentUser();
        Long response = buildingService.createBuilding(loggedUser,request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/faculties/{facultyId}")
    public ResponseEntity<List<BuildingModel>> getBuildingsForFaculty(@PathVariable Long facultyId){
        User loggedUser = authorizationService.getCurrentUser();
        List<BuildingModel> response = buildingService.getBuildingsForFaculty(loggedUser,facultyId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingDetailsResponse> getBuildingDetails(@PathVariable Long buildingId){
        User loggedUser = authorizationService.getCurrentUser();
        BuildingDetailsResponse response = buildingService.getBuildingDetails(loggedUser,buildingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{buildingId}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long buildingId){
        User loggedUser = authorizationService.getCurrentUser();
        buildingService.deleteBuilding(loggedUser,buildingId);
        return ResponseEntity.ok().build();
    }
}
