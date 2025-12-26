package com.example.School_System.repositories;

import com.example.School_System.entities.DeanInvitation;
import com.example.School_System.entities.valueObjects.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeanInvitationRepository extends JpaRepository<DeanInvitation,Long> {
    boolean existsByDeanEmailAndStatus(String deanEmail, InvitationStatus status);
    Optional<DeanInvitation> findByInvitationToken(String invitationToken);
}
