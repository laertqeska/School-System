package com.example.School_System.repositories;

import com.example.School_System.entities.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject,Long> {
}
