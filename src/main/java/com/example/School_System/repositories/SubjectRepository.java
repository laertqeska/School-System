package com.example.School_System.repositories;

import com.example.School_System.dto.subject.SchoolAdminSubjectModel;
import com.example.School_System.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    @Query("""
        SELECT new com.example.School_System.dto.subject.SchoolAdminSubjectModel(
            s.id, s.name
        )
        FROM Subject s
        WHERE s.department.id = :departmentId
    """)
    List<SchoolAdminSubjectModel> findByDepartment(@Param("departmentId") Long departmentId);
}
