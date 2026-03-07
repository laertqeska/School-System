package com.example.School_System.services;

import com.example.School_System.dto.attendance.*;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.AttendanceStatus;
import com.example.School_System.entities.valueObjects.SessionStatus;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final TeacherRepository teacherRepository;

    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository, ClassSessionRepository classSessionRepository, TeacherSubjectRepository teacherSubjectRepository, StudentRepository studentRepository, ScheduleRepository scheduleRepository, ClassroomRepository classroomRepository, SchoolClassRepository schoolClassRepository, TeacherRepository teacherRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.classSessionRepository = classSessionRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
        this.classroomRepository = classroomRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.teacherRepository = teacherRepository;
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

        boolean attendanceExists = classSessionRepository.existsByScheduleAndSessionDate(schedule,sessionDate);

        if(attendanceExists){
            throw new IllegalStateException("Attendance has already been taken for this session");
        }

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

        List<StudentAttendanceStatusModel> studentAttendanceList = validateStudents(request.getStudentAttendance(),studentList);

        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        for(StudentAttendanceStatusModel studentAttendance : studentAttendanceList){
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                    studentAttendance.getStudent(),
                    classSession,
                    studentAttendance.getAttendanceStatus(),
                    loggedUser
            );
            attendanceRecords.add(attendanceRecord);
        }
        attendanceRecordRepository.saveAll(attendanceRecords);
    }

    public List<StudentAttendanceStatusModel> validateStudents(List<StudentIdAttendanceStatusModel> studentAttendance, List<Student> studentList){
        if(studentList.size() != studentAttendance.size()){
            throw new IllegalArgumentException("Attendance must include all students of the class.");
        }
        studentAttendance.sort(Comparator.comparing(StudentIdAttendanceStatusModel::getId));
        studentList.sort(Comparator.comparing(Student::getId));
        List<StudentAttendanceStatusModel> studentAttendanceList = new ArrayList<>();
        for(int i = 0;i<studentList.size();i++){
            AttendanceStatus attendanceStatus = studentAttendance.get(i).getStatus();
            if(!studentList.get(i).getId().equals(studentAttendance.get(i).getId())){
                throw new IllegalStateException("Student does not belong to this class: " + studentAttendance.get(i).getId());
            }
            StudentAttendanceStatusModel studentAttendanceModel = new StudentAttendanceStatusModel(
                    studentList.get(i),
                    attendanceStatus
            );
            studentAttendanceList.add(studentAttendanceModel);
        }
        return studentAttendanceList;

    }

    public PaginatedClassAttendanceResponse getAttendanceForClass(User loggedUser, Long classId,int page,int perPage){
        if(page < 1){
            page = 0;
        }
        else page--;
        SchoolClass schoolClass = schoolClassRepository.findByIdAndIsDeletedFalse(classId)
                .orElseThrow(()->new EntityNotFoundException("School class not found for id: " + classId));
        Teacher teacher = teacherRepository.findByUserIdAndIsDeletedFalse(loggedUser.getId())
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found for user id: " + loggedUser.getId()));
        boolean teacherTeachesSchoolClass = teacherSubjectRepository.existsByTeacherAndSchoolClass(teacher,schoolClass);
        if(!teacherTeachesSchoolClass){
            throw new AccessDeniedException("Teacher does not teach to this class");
        }
        Pageable pageable = PageRequest.of(page,perPage);
        Page<StudentAttendanceModel> studentAttendancePage = attendanceRecordRepository.getAttendanceForClass(classId,AttendanceStatus.ABSENT,pageable);
        return new PaginatedClassAttendanceResponse(
                studentAttendancePage.getContent(),
                page + 1,
                perPage,
                studentAttendancePage.getTotalElements(),
                studentAttendancePage.getTotalPages()
        );
    }

}
