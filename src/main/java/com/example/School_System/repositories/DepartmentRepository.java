package com.example.School_System.repositories;

import com.example.School_System.dto.department.DepartmentModelResponse;
import com.example.School_System.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("SELECT new com.example.School_System.dto.department.DepartmentModelResponse(d.id,d.name) " +
            "FROM Department d " +
            "WHERE d.faculty.id = :facultyId " +
            "AND d.isDeleted = false")
    List<DepartmentModelResponse> findDepartmentModelsByFacultyId(@Param("facultyId") Long facultyId);

    List<Department> findByFacultyId(Long facultyId);

    Optional<Department> findByIdAndIsDeletedFalse(Long departmentId);

    boolean existsByFacultyIdAndName(Long facultyId,String name);
}
