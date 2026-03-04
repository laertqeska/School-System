package com.example.School_System.services;

import com.example.School_System.dto.building.BuildingDetailsResponse;
import com.example.School_System.dto.building.BuildingModel;
import com.example.School_System.dto.building.CreateBuildingRequest;
import com.example.School_System.entities.Building;
import com.example.School_System.entities.Faculty;
import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.repositories.BuildingRepository;
import com.example.School_System.repositories.FacultyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final FacultyRepository facultyRepository;
    private final SchoolContextService schoolContextService;

    public BuildingService(BuildingRepository buildingRepository, FacultyRepository facultyRepository, SchoolContextService schoolContextService) {
        this.buildingRepository = buildingRepository;
        this.facultyRepository = facultyRepository;
        this.schoolContextService = schoolContextService;
    }

    @Transactional
    public Long createBuilding(User loggedUser, CreateBuildingRequest request){
        School adminSchool = schoolContextService.resolveSchool(loggedUser);
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(()-> new EntityNotFoundException("Faculty not found for id:" + request.getFacultyId()));
        if(!faculty.getSchool().getId().equals(adminSchool.getId())){
            throw new AccessDeniedException("This faculty does not exist in your school");
        }
        checkIfBuildingAlreadyExists(faculty,request.getBuildingName(),request.getBuildingCode());
        Building building = new Building(
                faculty,
                request.getBuildingName(),
                request.getBuildingCode()
        );
        Building createdBuilding = buildingRepository.save(building);
        return createdBuilding.getId();
    }


    private void checkIfBuildingAlreadyExists(Faculty faculty,String buildingName,String buildingCode){
        boolean buildingAlreadyExists = buildingRepository.existsByFacultyAndBuildingNameAndBuildingCode(faculty,buildingName,buildingCode);
        boolean buildingExistsWithName = buildingRepository.existsByFacultyAndBuildingName(faculty,buildingName);
        boolean buildingExistsWithCode = buildingRepository.existsByFacultyAndBuildingCode(faculty,buildingCode);
        if(buildingAlreadyExists){
            throw new IllegalArgumentException("Building already exists with name and code");
        }
        if(buildingExistsWithName){
            throw new IllegalArgumentException("Building already exists with name: " + buildingName);
        }
        if(buildingExistsWithCode){
            throw new IllegalArgumentException("Building already exists with code: " + buildingCode);
        }
    }

    public List<BuildingModel> getBuildingsForFaculty(User loggedUser, Long facultyId){
        School adminSchool = schoolContextService.resolveSchool(loggedUser);
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(()-> new EntityNotFoundException("Faculty not found for id: " + facultyId));
        if(!faculty.getSchool().getId().equals(adminSchool.getId())){
            throw new AccessDeniedException("Faculty does not belong to your school");
        }
        return buildingRepository.getBuildingsForFaculty(facultyId);
    }

    public BuildingDetailsResponse getBuildingDetails(User loggedUser, Long buildingId){
        School adminSchool = schoolContextService.resolveSchool(loggedUser);
        Building building = buildingRepository.findByIdAndIsDeletedFalse(buildingId)
                .orElseThrow(()-> new EntityNotFoundException("Building not found for id: " + buildingId));
        if(!building.getFaculty().getSchool().getId().equals(adminSchool.getId())){
            throw new AccessDeniedException("Building does not belong to your school");
        }
        String facultyName = building.getFaculty().getName();
        return new BuildingDetailsResponse(
                building.getBuildingName(),
                building.getBuildingCode(),
                facultyName
        );
    }

    @Transactional
    public void deleteBuilding(User loggedUser, Long buildingId){
        School adminSchool = schoolContextService.resolveSchool(loggedUser);
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(()->new EntityNotFoundException("Building not found for id: " + buildingId));
        if(!building.getFaculty().getSchool().getId().equals(adminSchool.getId())){
            throw new AccessDeniedException("Building does not belong to your school");
        }
        building.delete(loggedUser);
        buildingRepository.save(building);
    }


}
