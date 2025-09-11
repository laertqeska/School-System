package com.example.School_System.repositories;

import com.example.School_System.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolRepository extends JpaRepository<School,Long> {
    Page<School> findAll(Pageable pageable);
}
