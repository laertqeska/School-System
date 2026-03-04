package com.example.School_System.services;

import com.example.School_System.dto.attendance.StudentAttendanceModel;
import com.example.School_System.dto.attendance.StudentIdAttendanceModel;
import com.example.School_System.dto.attendance.TakeAttendanceRequest;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.AttendanceStatus;
import com.example.School_System.entities.valueObjects.SessionStatus;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final ClassSessionRepository classSessionRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClassroomRepository classroomRepository;
    private final SchoolClassRepository schoolClassRepository;

    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository, ClassSessionRepository classSessionRepository, TeacherSubjectRepository teacherSubjectRepository, StudentRepository studentRepository, ScheduleRepository scheduleRepository, ClassroomRepository classroomRepository, SchoolClassRepository schoolClassRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.classSessionRepository = classSessionRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
        this.classroomRepository = classroomRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Transactional
    public void takeAttendance(User loggedUser, TakeAttendanceRequest request){
        SchoolClass schoolClass = schoolClassRepository.findByIdAndIsDeletedFalse(request.getSchoolClassId())
                .orElseThrow(()-> new EntityNotFoundException("School class not found for id: " + request.getSchoolClassId()));
        Teacher teacher = loggedUser.getTeacher();
        boolean teacherTeachesClass = teacherSubjectRepository.existsByTeacherAndSchoolClass(teacher,schoolClass);
        if(!teacherTeachesClass){
            throw new AccessDeniedException("You do not have access to this class");
        }
        Long scheduleId = request.getScheduleId();
        Schedule schedule = scheduleRepository.findByIdAndIsDeletedFalse(scheduleId)
                .orElseThrow(()-> new EntityNotFoundException("Schedule not found for id: " + scheduleId));

        String topic = request.getTopic();
        Long classroomId = request.getClassroomId() != null ? request.getClassroomId() : schedule.getClassroom().getId();
        Classroom classroom = classroomRepository.findByIdAndIsDeletedFalse(classroomId)
                .orElseThrow(()-> new EntityNotFoundException("Classroom not found for id: " + classroomId));
        String notes = request.getNotes() != null ? request.getNotes() : null;
        LocalTime startTime = request.getStartTime() != null ? request.getStartTime() : schedule.getStartTime();
        LocalTime endTime = request.getEndTime() != null ? request.getEndTime() : schedule.getEndTime();
        LocalDate sessionDate = request.getSessionDate();

        ClassSession classSession = new ClassSession(
                schedule,
                sessionDate,
                startTime,
                endTime,
                topic,
                SessionStatus.COMPLETED,
                classroom
        );

        classSessionRepository.save(classSession);

        List<Student> studentList = studentRepository.findAllBySchoolClass_AndIsDeletedFalse(schoolClass);

        List<StudentAttendanceModel> studentAttendanceList = validateStudents(request.getStudentAttendance(),studentList);

        for(StudentAttendanceModel studentAttendance : studentAttendanceList){
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                    studentAttendance.getStudent(),
                    classSession,
                    studentAttendance.getAttendanceStatus(),
                    loggedUser
            );
            attendanceRecordRepository.save(attendanceRecord);
        }
    }

    public List<StudentAttendanceModel> validateStudents(List<StudentIdAttendanceModel> studentAttendance, List<Student> studentList){
        if(studentList.size() != studentAttendance.size()){
            throw new IllegalArgumentException("Attendance must include all students of the class.");
        }
        studentAttendance.sort(Comparator.comparing(StudentIdAttendanceModel::getId));
        studentList.sort(Comparator.comparing(Student::getId));
        List<StudentAttendanceModel> studentAttendanceList = new ArrayList<>();
        for(int i = 0;i<studentList.size();i++){
            AttendanceStatus attendanceStatus = studentAttendance.get(i).getStatus();
            if(!studentList.get(i).getId().equals(studentAttendance.get(i).getId())){
                throw new IllegalStateException("Student does not belong to this class: " + studentAttendance.get(i).getId());
            }
            StudentAttendanceModel studentAttendanceModel = new StudentAttendanceModel(
                    studentList.get(i),
                    attendanceStatus
            );
            studentAttendanceList.add(studentAttendanceModel);
        }
        return studentAttendanceList;

    }

}
