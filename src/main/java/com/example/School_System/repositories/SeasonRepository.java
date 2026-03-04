package com.example.School_System.repositories;

import com.example.School_System.dto.season.SeasonDetailsResponse;
import com.example.School_System.entities.Season;
import com.example.School_System.entities.valueObjects.SeasonStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season,Long> {
    Optional<Season> findByFaculty_IdAndIsDeletedFalseAndStatus(Long facultyId, SeasonStatus status);
    boolean existsByFaculty_IdAndCodeAndIsDeletedFalse(Long facultyId,String code);

    @Query("SELECT new com.example.School_System.dto.season.SeasonDetailsResponse(s.id,s.name,s.code,s.seasonType,s.startDate,s.endDate,CONCAT(a.startDate,CONCAT('-',a.endDate))) " +
            "FROM Season s " +
            "JOIN s.academicYear a " +
            "WHERE s.faculty.id = :facultyId " +
            "AND s.status = :status " +
            "AND s.isDeleted = false")
    Page<SeasonDetailsResponse> findByFaculty_IdAndStatusAndIsDeletedFalse(@Param("facultyId") Long facultyId, @Param("status") SeasonStatus status, Pageable pageable);



    @Query("SELECT new com.example.School_System.dto.season.SeasonDetailsResponse(s.id,s.name,s.code,s.seasonType,s.startDate,s.endDate,CONCAT(a.startDate,CONCAT('-',a.endDate))) " +
            "FROM Season s " +
            "JOIN s.academicYear a " +
            "WHERE s.faculty.id = :facultyId " +
            "AND s.isDeleted = false")
    Page<SeasonDetailsResponse> findByFaculty_IdAndIsDeletedFalse(Long facultyId,Pageable pageable);
}
