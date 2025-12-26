package com.example.School_System.repositories;

import com.example.School_System.entities.FacultyApprovalToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyApprovalTokenRepository extends JpaRepository<FacultyApprovalToken,Long> {
    Optional<FacultyApprovalToken> findByToken(String token);
}
