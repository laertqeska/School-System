package com.example.School_System.repositories;

import com.example.School_System.dto.grade.StudentGradeModel;
import com.example.School_System.dto.grade.TeachersGradeModel;
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

    @Query("SELECT new com.example.School_System.dto.grade.TeachersGradeModel(student.user.firstName,student.user.lastName,class.name,g.score,g.maxScore) " +
            "FROM Grade g " +
            "JOIN g.student student " +
            "JOIN student.schoolClass class " +
            "WHERE g.teacher.id = :teacherId AND g.studyProgramSubject.id = :studyProgramSubjectId " +
            "AND (g.schoolClass.id = :classId) " +
            "AND (:score IS NULL OR g.score = :score) " +
            "AND (:search IS NULL OR " +
            "LOWER(CONCAT(g.student.user.firstName, ' ', g.student.user.lastName)) LIKE CONCAT('%', :search, '%'))")
    Page<TeachersGradeModel> getGradesForTeacher(@Param("teacherId") Long teacherId,
                                                 @Param("search") String search,
                                                 @Param("studyProgramSubjectId") Long studyProgramSubjectId,
                                                 @Param("classId") Long classId,
                                                 @Param("score") BigDecimal score,
                                                 Pageable pageable);

    @Query("SELECT new com.example.School_System.dto.grade.StudentGradeModel(sps.subject.name,sps.credits,g.score,g.maxScore) FROM Grade g " +
            "JOIN g.studyProgramSubject sps " +
            "WHERE g.student.id = :studentId " +
            "AND (:semester IS NULL OR sps.semester = :semester)" +
            "AND (:year IS NULL OR sps.yearLevel = :year)")
    Page<StudentGradeModel> getGradesForStudent(@Param("studentId") Long studentId, Pageable pageable, @Param("semester") Integer semester, @Param("year") Integer year);

}
