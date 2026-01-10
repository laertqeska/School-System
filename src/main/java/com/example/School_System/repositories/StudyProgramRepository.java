package com.example.School_System.repositories;

import com.example.School_System.entities.StudyProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram,Long> {

    @Query("SELECT sp " +
            "FROM StudyProgram sp " +
            "WHERE sp.department.id = :departmentId AND " +
            "(:studyProgramName IS NULL OR LOWER(sp.name) LIKE LOWER(CONCAT('%',:studyProgramName,'%'))) OR  " +
            "(:degreeLevel IS NULL OR LOWER(sp.name) LIKE LOWER(CONCAT('%',:degreeLevel,'%')))")
    Page<StudyProgram> getStudyProgramsForSchool(Pageable pageable, @Param("departmentId") Long departmentId, @Param("studyProgramName") String studyProgramName, @Param("degreeLevel") String degreeLevel);


    @Query("SELECT sp FROM StudyProgram sp " +
            "JOIN sp.department d " +
            "WHERE d.faculty.id = :facultyId AND " +
            "(:studyProgramName IS NULL OR LOWER(sp.name) LIKE LOWER(CONCAT('%', :studyProgramName, '%'))) AND " +
            "(:degreeLevel IS NULL OR LOWER(sp.degreeLevel) = LOWER(:degreeLevel))")
    Page<StudyProgram> getPaginatedStudyProgramsForFaculty(
            Pageable pageable,
            @Param("facultyId") Long facultyId,
            @Param("studyProgramName") String studyProgramName,
            @Param("degreeLevel") String degreeLevel
    );

    List<StudyProgram> findByDepartmentId(Long departmentId);

    List<StudyProgram> findByDepartmentFacultyId(Long facultyId);
}
