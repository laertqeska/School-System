package com.example.School_System.repositories;

import com.example.School_System.dto.schoolAdmin.SchoolAdminModel;
import com.example.School_System.entities.School;
import com.example.School_System.entities.SchoolAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolAdminRepository extends JpaRepository<SchoolAdmin,Long> {
    Optional<SchoolAdmin> findByUserId(Long userId);

    @Query("SELECT new com.example.School_System.dto.schoolAdmin.SchoolAdminModel(user.firstName,user.lastName,user.email,school.name) " +
            "FROM SchoolAdmin admin " +
            "JOIN admin.user user " +
            "JOIN admin.school school " +
            "WHERE (:search IS NULL OR " +
            "LOWER(user.firstName) LIKE CONCAT('%',:search,'%') OR "+
            "LOWER(user.lastName) LIKE CONCAT('%',:search,'%') OR " +
            "LOWER(user.email) LIKE CONCAT('%',:search,'%') OR " +
            "LOWER(school.name) LIKE CONCAT('%',:search,'%'))")
    Page<SchoolAdminModel> findSchoolAdminsWithSearch(Pageable pageable, @Param("search") String search);


    @Query("SELECT s " +
            "FROM SchoolAdmin admin " +
            "JOIN admin.user user " +
            "JOIN admin.school s " +
            "WHERE user.id = :userId")
    Optional<School> findSchoolForSchoolAdmin(@Param("userId") Long userId);
}
