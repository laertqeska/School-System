package com.example.School_System.repositories;

import com.example.School_System.dto.studyProgramSubject.TeacherStudyProgramSubjectModel;
import com.example.School_System.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    boolean existsByTeacherAndSchoolClass(Teacher teacher,SchoolClass schoolClass);

    @Query("SELECT CONCAT(studyProgram.name,' - ',subject.name,' (',studyProgram.degreeLevel,')')  " +
            "FROM TeacherSubject ts " +
            "JOIN ts.studyProgramSubject studyProgramSubject " +
            "JOIN studyProgramSubject.studyProgram studyProgram " +
            "JOIN studyProgramSubject.subject subject " +
            "WHERE ts.teacher.id = :teacherId")
    List<String> getTeacherSubjectNamesForTeacher(@Param("teacherId") Long teacherId);

    @Query("SELECT ts.schoolClass.name " +
            "FROM TeacherSubject ts " +
            "WHERE ts.teacher.id = :teacherId")
    List<String> getClassesNamesForTeacher(@Param("teacherId") Long teacherId);

    @Query("SELECT new com.example.School_System.dto.studyProgramSubject.TeacherStudyProgramSubjectModel(studyProgramSubject.id,studyProgramSubject.subject.name) " +
            "FROM TeacherSubject ts " +
            "JOIN ts.studyProgramSubject studyProgramSubject " +
            "JOIN ts.teacher teacher " +
            "WHERE teacher.id = :teacherId")
    List<TeacherStudyProgramSubjectModel> getTeacherStudyProgramSubjectModel(@Param("teacherId") Long teacherId);

    Optional<TeacherSubject> findByTeacherAndSchoolClassAndStudyProgramSubject(Teacher teacher,SchoolClass schoolClass,StudyProgramSubject studyProgramSubject);
}
