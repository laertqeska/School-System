package com.example.School_System.repositories;

import com.example.School_System.entities.School;
import com.example.School_System.entities.User;
import com.example.School_System.entities.valueObjects.SchoolType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {
    Page<School> findAll(Pageable pageable);

    List<School> findByIsActiveTrue();

    List<School> findBySchoolType(SchoolType schoolType);

    List<School> findByCity(String city);

    Optional<School> findByLicenseNumber(String licenseNumber);

    Optional<School> findByRector(User rector);

    boolean existsByLicenseNumber(String licenseNumber);
}
