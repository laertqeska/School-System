package com.example.School_System.repositories;

import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT new com.example.School_System.dto.department.DepartmentModelResponse(d.id,d.name) " +
            "FROM Department d " +
            "WHERE d.faculty.id = :facultyId")
    List<DepartmentModelResponse> findByFacultyId(@Param("facultyId") Long facultyId);
}
