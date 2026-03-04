package com.example.School_System.repositories;

import com.example.School_System.dto.building.BuildingModel;
import com.example.School_System.entities.Building;
import com.example.School_System.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building,Long> {
    boolean existsByFacultyAndBuildingNameAndBuildingCode(Faculty faculty, String buildingName, String buildingCode);
    boolean existsByFacultyAndBuildingName(Faculty faculty,String buildingName);
    boolean existsByFacultyAndBuildingCode(Faculty faculty,String buildingCode);

    Optional<Building> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT new com.example.School_System.dto.building.BuildingModel(b.buildingName,b.buildingCode) " +
            "FROM Building b " +
            "WHERE b.faculty.id = :facultyId " +
            "AND b.isDeleted = false")
    List<BuildingModel> getBuildingsForFaculty(@Param("facultyId") Long facultyId);
}
