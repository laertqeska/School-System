package com.example.School_System.repositories;

import com.example.School_System.dto.attendance.PaginatedClassAttendanceResponse;
import com.example.School_System.dto.attendance.StudentAttendanceModel;
import com.example.School_System.entities.AttendanceRecord;
import com.example.School_System.entities.valueObjects.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord,Long> {
    @Query("SELECT new com.example.School_System.dto.attendance.StudentAttendanceModel(student.id,student.user.firstName,student.user.lastName,count(a.id)) " +
            "FROM AttendanceRecord a " +
            "JOIN a.student student " +
            "WHERE a.status = :status " +
            "AND student.schoolClass.id = :classId " +
            "GROUP BY student.id,student.user.firstName,student.user.lastName"
    )
    Page<StudentAttendanceModel> getAttendanceForClass(@Param("classId") Long classId, @Param("status") AttendanceStatus status,Pageable pageable);
}
