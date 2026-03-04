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
             "AND (LOWER(f.name) LIKE LOWER(CONCAT('%',:search,'%'))) " +
             "AND f.isDeleted = false")
     Page<FacultyModel> getFaculties(
             Pageable pageable,
             @Param("search") String search,
             @Param("schoolId") Long schoolId
     );


     @Query("SELECT new com.example.School_System.dto.rector.RectorFacultiesModel(f.id,f.name,f.code,d.firstName,d.lastName) " +
             "FROM Faculty f " +
             "LEFT JOIN f.dean d " +
             "WHERE f.school.id = :schoolId AND f.approvalStatus = :status " +
             "AND f.isDeleted = false")
     Page<RectorFacultiesModel> getFacultiesForRector(Pageable pageable, Long schoolId, @Param("status") ApprovalStatus status);


     @Query("SELECT f.school " +
             "FROM Faculty f " +
             "WHERE f.dean.id = :userId " +
             "AND f.isDeleted = false")
     Optional<School> findSchoolForDean(@Param("userId") Long userId);


     @Query("SELECT s.studyProgram.department.faculty " +
             "FROM StudyProgramSubject s " +
             "WHERE s.id = :studyProgramSubjectId")
     Faculty getFacultyForStudyProgramSubject(@Param("studyProgramSubjectId") Long studyProgramSubjectId);
}
