package com.example.School_System.repositories;

import com.example.School_System.dto.studyProgramSubject.TeacherStudyProgramSubjectModel;
import com.example.School_System.entities.StudyProgramSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyProgramSubjectRepository extends JpaRepository<StudyProgramSubject,Long> {
    @Query("SELECT sps " +
            "FROM StudyProgramSubject sps " +
            "WHERE sps.studyProgram.department = :departmentId")
    List<StudyProgramSubject> findStudyProgramSubjectByDepartment(@Param("departmentId") Long departmentId);

    List<StudyProgramSubject> findByStudyProgramDepartmentFacultyId(Long facultyId);
}
