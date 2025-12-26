package com.example.School_System.repositories;

import com.example.School_System.entities.RectorInvitation;
import com.example.School_System.entities.valueObjects.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RectorInvitationRepository extends JpaRepository<RectorInvitation,Long> {
    boolean existsByRectorEmailAndStatus(String rectorEmail, InvitationStatus status);
    Optional<RectorInvitation> findByInvitationToken(String invitationToken);
}
