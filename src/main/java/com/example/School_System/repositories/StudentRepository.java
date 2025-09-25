package com.example.School_System.repositories;

import com.example.School_System.entities.Student;
import com.example.School_System.entities.valueObjects.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Page<Student> findAll(Pageable pageable);

    Optional<Student> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

    List<Student> findBySchool_Id(Long schoolId);


    List<Student> findByStudyProgram_Id(Long studyProgramId);


    List<Student> findByStatus(StudentStatus status);

    long countBySchool_Id(Long schoolId);

    Optional<Student> findByUserId(Long userId);
}
