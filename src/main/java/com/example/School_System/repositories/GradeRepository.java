package com.example.School_System.repositories;

import com.example.School_System.entities.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

    @Query("SELECT g FROM Grade g " +
            "WHERE g.studyProgramSubject.id = :studyProgramSubjectId " +
            "AND (:classId IS NULL OR g.schoolClass.id = :classId) " +
            "AND (:score IS NULL OR g.score = :score) " +
            "AND (:search IS NULL OR " +
            "LOWER(CONCAT(g.student.user.firstName, ' ', g.student.user.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Grade> getGradesForTeacher(@Param("search") String search,
                          @Param("studyProgramSubjectId") Long studyProgramSubjectId,
                          @Param("classId") Long classId,
                          @Param("score") BigDecimal score,
                          Pageable pageable);

    @Query("SELECT g FROM Grade g " +
            "WHERE g.student.id = :studentId ")
    Page<Grade> getGradesForStudent(@Param("studentId") Long studentId,Pageable pageable);

}
