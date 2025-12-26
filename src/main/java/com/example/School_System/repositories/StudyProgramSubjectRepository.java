package com.example.School_System.repositories;

import com.example.School_System.entities.StudyProgramSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProgramSubjectRepository extends JpaRepository<StudyProgramSubject,Long> {
}
