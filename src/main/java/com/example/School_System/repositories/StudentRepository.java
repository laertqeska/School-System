package com.example.School_System.repositories;

import com.example.School_System.dto.student.StudentModel;
import com.example.School_System.entities.School;
import com.example.School_System.entities.Student;
import com.example.School_System.entities.valueObjects.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

    @Query("""
    SELECT new com.example.School_System.dto.student.StudentModel(
        s.id,
        u.firstName,
        u.lastName,
        s.studentId,
        CONCAT(sp.degreeLevel, '-', sp.name)
    )
    FROM Student s
    JOIN s.user u
    JOIN s.studyProgram sp
    WHERE s.school.id = :schoolId
""")
    Page<StudentModel> findStudentModelsBySchoolId(
            @Param("schoolId") Long schoolId,
            Pageable pageable
    );



    List<Student> findByStudyProgram_Id(Long studyProgramId);


    List<Student> findByStatus(StudentStatus status);

    long countBySchool_Id(Long schoolId);

    Optional<Student> findByUserId(Long userId);

    @Query("SELECT school " +
            "FROM Student s " +
            "JOIN s.user user " +
            "JOIN s.school school " +
            "WHERE user.id = :userId")
    Optional<School> findSchoolForStudent(@Param("userId") Long userId);
}
