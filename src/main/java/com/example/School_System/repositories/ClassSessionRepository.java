package com.example.School_System.repositories;

import com.example.School_System.entities.ClassSession;
import com.example.School_System.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession,Long> {
    boolean existsByScheduleAndSessionDate(Schedule schedule, LocalDate sessionDate);
}
