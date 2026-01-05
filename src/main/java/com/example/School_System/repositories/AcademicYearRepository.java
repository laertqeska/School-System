package com.example.School_System.repositories;

import com.example.School_System.entities.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear,Long> {
    Optional<AcademicYear> findBySchoolIdAndIsCurrentTrue(Long schoolId);

}
