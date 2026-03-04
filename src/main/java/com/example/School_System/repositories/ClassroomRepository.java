package com.example.School_System.repositories;

import com.example.School_System.dto.classroom.AvailableClassroomModel;
import com.example.School_System.dto.classroom.ClassroomForBuildingModel;
import com.example.School_System.entities.Classroom;
import com.example.School_System.entities.valueObjects.ClassroomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Long> {
    Optional<Classroom> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT new com.example.School_System.dto.classroom.ClassroomForBuildingModel(c.id,c.classroomNumber,c.classroomName,c.classroomType,c.capacity,c.hasProjector,c.isAccessible) " +
            "FROM Classroom c " +
            "WHERE c.isDeleted = false " +
            "AND c.building.id = :buildingId " +
            "AND (:type IS NULL OR c.classroomType = :type) " +
            "AND ((LOWER(c.classroomName) LIKE CONCAT('%',:search,'%')) " +
            "OR (LOWER(c.classroomNumber) LIKE CONCAT('%',:search,'%'))) ")
    Page<ClassroomForBuildingModel> getPaginatedClassroomsForBuilding(Pageable pageable, @Param("buildingId") Long buildingId, @Param("search") String search, @Param("type")ClassroomType classroomType);



    @Query("""
        SELECT new com.example.School_System.dto.classroom.AvailableClassroomModel(
            c.id,
            c.classroomNumber,
            CONCAT(c.classroomName,CONCAT(' ',b.buildingCode)),
            c.classroomType,
            c.capacity,
            c.hasProjector,
            c.isAccessible
        )
        FROM Classroom c
        JOIN c.building b
        WHERE b.faculty.id = :facultyId
          AND c.isDeleted = false
          AND (:buildingId IS NULL OR b.id = :buildingId)
          AND (:classroomType IS NULL OR c.classroomType = :classroomType)
          AND (:isAccessible IS NULL OR c.isAccessible = :isAccessible)
          AND (:hasProjector IS NULL OR c.hasProjector = :hasProjector)
          AND ((c.classroomName LIKE CONCAT('%',:search,'%'))
          OR (c.classroomNumber LIKE CONCAT('%',:search,'%')))
          AND NOT EXISTS (
            SELECT 1 FROM Schedule s
            WHERE s.classroom = c
              AND s.isDeleted = false
              AND s.isActive = true
              AND s.dayOfWeek = :dayOfWeek
              AND s.season.id = :seasonId
              AND :startTime < s.endTime
              AND :endTime > s.startTime
          )
        ORDER BY c.classroomNumber
        """)
    Page<AvailableClassroomModel> getPaginatedAvailableClassrooms(
            Pageable pageable,
            @Param("facultyId") Long facultyId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("seasonId") Long seasonId,
            @Param("buildingId") Long buildingId,
            @Param("search") String search,
            @Param("hasProjector") boolean hasProjector,
            @Param("isAccessible") boolean isAccessible,
            @Param("classroomType") ClassroomType classroomType
    );
}
