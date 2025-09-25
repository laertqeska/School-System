package com.example.School_System.repositories;

import com.example.School_System.entities.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudyProgramRepository extends JpaRepository<StudyProgram,Long> {
}
