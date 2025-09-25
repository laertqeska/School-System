package com.example.School_System.repositories;

import com.example.School_System.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Page<Teacher> findBySchoolId(Pageable pageable,Long schoolId);

    @Query("SELECT t FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.department d " +
            "WHERE t.school.id = :schoolId AND (" +
            ":search IS NULL OR (" +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :search, '%'))))")
    Page<Teacher> findTeachersWithSearch(@Param("search") String search,
                                         @Param("schoolId") Long schoolId,
                                         Pageable pageable);


}
