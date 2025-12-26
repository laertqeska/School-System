package com.example.School_System.repositories;

import com.example.School_System.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject,Long> {
    @Query("SELECT COUNT(ts) > 0 FROM TeacherSubject ts JOIN ts.schoolClass.students s WHERE ts.teacher.id = :teacherId AND s.id = :studentId")
    boolean existsByTeacherIdAndStudentId(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId);
    boolean existsByTeacherAndStudyProgramSubjectAndSchoolClassAndAcademicYear(
            Teacher teacher,
            StudyProgramSubject studyProgramSubject,
            SchoolClass schoolClass,
            AcademicYear academicYear
    );


}
