package com.example.School_System.repositories;

import com.example.School_System.dto.faculty.FacultyModel;
import com.example.School_System.dto.rector.RectorFacultiesModel;
import com.example.School_System.entities.Faculty;
import com.example.School_System.entities.School;
import com.example.School_System.entities.valueObjects.ApprovalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
     @Query("SELECT new com.example.School_System.dto.faculty.FacultyModel(f.id, f.name) " +
             "FROM Faculty f " +
             "WHERE f.school.id = :schoolId " +
             "AND (:search IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%',:search,'%')))")
     Page<FacultyModel> getFaculties(
             Pageable pageable,
             @Param("search") String search,
             @Param("schoolId") Long schoolId
     );


     @Query("SELECT new com.example.School_System.dto.rector.RectorFacultiesModel(f.name,f.code,d.firstName,d.lastName) " +
             "FROM Faculty f " +
             "LEFT JOIN f.dean d " +
             "WHERE f.school.id = :schoolId AND f.approvalStatus = :status")
     Page<RectorFacultiesModel> getFacultiesForRector(Pageable pageable, Long schoolId, @Param("status") ApprovalStatus status);


     @Query("SELECT f.school " +
             "FROM Faculty f " +
             "WHERE f.dean.id = :userId")
     Optional<School> findSchoolForDean(@Param("userId") Long userId);
}
