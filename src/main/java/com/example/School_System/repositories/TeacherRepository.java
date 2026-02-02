package com.example.School_System.repositories;

import com.example.School_System.dto.teacher.TeacherModel;
import com.example.School_System.entities.School;
import com.example.School_System.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Page<Teacher> findBySchoolId(Pageable pageable,Long schoolId);

    @Query("SELECT new com.example.School_System.dto.teacher.TeacherModel(u.firstName,u.lastName,u.email,t.department.name,t.academicTitle) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.department d " +
            "WHERE t.school.id = :schoolId " +
            "AND (:search IS NULL OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<TeacherModel> findTeachersWithSearch(@Param("search") String search,
                                         @Param("schoolId") Long schoolId,
                                         Pageable pageable);

    @Query("SELECT new com.example.School_System.dto.teacher.TeacherModel(u.firstName, u.lastName, u.email, d.name,  t.academicTitle) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.department d " +
            "WHERE t.school.id = :schoolId " +
            "AND d.faculty.id = :facultyId " +
            "AND (:search IS NULL OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<TeacherModel> findTeachersWithSearchByFaculty(
            @Param("search") String search,
            @Param("schoolId") Long schoolId,
            @Param("facultyId") Long facultyId,
            Pageable pageable);

    Optional<Teacher> findByUserId(Long userId);

    @Query("SELECT s " +
            "FROM Teacher t " +
            "JOIN t.user user " +
            "JOIN t.school s " +
            "WHERE user.id = :userId")
    Optional<School> findSchoolForTeacher(@Param("userId") Long userId);
}
