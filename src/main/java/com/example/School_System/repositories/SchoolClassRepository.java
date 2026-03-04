package com.example.School_System.repositories;

import com.example.School_System.dto.schoolClass.TeacherClassesModel;
import com.example.School_System.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {
    @Query("SELECT new com.example.School_System.dto.schoolClass.TeacherClassesModel(sc.id, sc.name) " +
            "FROM TeacherSubject ts " +
            "JOIN ts.schoolClass sc " +
            "WHERE ts.teacher.id = :teacherId")
    List<TeacherClassesModel> getClassesForTeacher(@Param("teacherId") Long teacherId);

    Optional<SchoolClass> findByIdAndIsDeletedFalse(Long id);
}
