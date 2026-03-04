package com.example.School_System.repositories;

import com.example.School_System.dto.schedule.ScheduleForClassModel;
import com.example.School_System.dto.schedule.TeacherScheduleModel;
import com.example.School_System.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    Optional<Schedule> findByIdAndIsDeletedFalse(Long id);
    @Query("SELECT new com.example.School_System.dto.schedule.ScheduleForClassModel(sps.subject.name,s.sessionType,CONCAT(teacher.user.firstName,CONCAT(' ',teacher.user.lastName)),s.classroom.classroomNumber,s.dayOfWeek,s.startTime,s.endTime) " +
            "FROM Schedule s " +
            "JOIN s.teacherSubject ts " +
            "JOIN ts.studyProgramSubject sps " +
            "JOIN ts.teacher teacher " +
            "WHERE ts.schoolClass.id = :schoolClassId " +
            "ORDER BY s.dayOfWeek,s.startTime")
    List<ScheduleForClassModel> getScheduleForSchoolClass(@Param("schoolClassId") Long schoolClassId);

    @Query("SELECT new com.example.School_System.dto.schedule.TeacherScheduleModel(studyProgram.name,teacherSubject.studyProgramSubject.subject.name,s.sessionType,schoolClass.yearLevel,schoolClass.name,CONCAT(classroom.classroomNumber,classroom.building.buildingCode),s.dayOfWeek) " +
            "FROM Schedule s " +
            "JOIN s.classroom classroom " +
            "JOIN s.teacherSubject teacherSubject " +
            "JOIN teacherSubject.schoolClass schoolClass " +
            "JOIN schoolClass.studyProgram studyProgram " +
            "WHERE teacherSubject.teacher.id = :teacherId " +
            "ORDER BY s.dayOfWeek,s.startTime")
    List<TeacherScheduleModel> getTeacherSchedule(@Param("teacherId") Long teacherId);
}
