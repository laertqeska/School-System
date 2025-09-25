package com.example.School_System.repositories;

import com.example.School_System.entities.Role;
import com.example.School_System.entities.valueObjects.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
