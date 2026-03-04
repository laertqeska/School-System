package com.example.School_System.services;

import com.example.School_System.dto.schedule.*;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.SeasonStatus;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final SeasonRepository seasonRepository;
    private final FacultyRepository facultyRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, TeacherRepository teacherRepository, ClassroomRepository classroomRepository, SchoolClassRepository schoolClassRepository, TeacherSubjectRepository teacherSubjectRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, SchoolAdminRepository schoolAdminRepository, SeasonRepository seasonRepository, FacultyRepository facultyRepository) {
        this.scheduleRepository = scheduleRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.seasonRepository = seasonRepository;
        this.facultyRepository = facultyRepository;
    }

    @Transactional
    public Long createSchedule(User loggedUser, CreateScheduleRequest request){
        Long userId = loggedUser.getId();
        Long teacherId = request.getTeacherId();
        Long classroomId = request.getClassroomId();
        Long schoolClassId = request.getSchoolClassId();
        Long studyProgramSubjectId = request.getStudyProgramSubjectId();
        Long seasonId = request.getSeasonId();
        Teacher headOfDepartment = teacherRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()->new EntityNotFoundException("Teacher not found for user id: " + userId));
        Teacher teacher = teacherRepository.findByIdAndIsDeletedFalse(teacherId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found for id: " + teacherId));
        if(!teacher.getDepartment().getId().equals(headOfDepartment.getHeadOfDepartment().getId())){
            throw new AccessDeniedException("Teacher does not belong to your department");
        }
        Classroom classroom = classroomRepository.findByIdAndIsDeletedFalse(classroomId)
                .orElseThrow(()->new EntityNotFoundException("Classroom not found for id: " + classroomId));
        SchoolClass schoolClass = schoolClassRepository.findByIdAndIsDeletedFalse(schoolClassId)
                .orElseThrow(()->new EntityNotFoundException("School class not found for id: " + schoolClassId));
        if(!schoolClass.getStudyProgram().getDepartment().getId().equals(headOfDepartment.getHeadOfDepartment().getId())){
            throw new AccessDeniedException("This school class is not in your department");
        }
        StudyProgramSubject studyProgramSubject = studyProgramSubjectRepository.findByIdAndIsDeletedFalse(studyProgramSubjectId)
                .orElseThrow(() -> new EntityNotFoundException("Study Program Subject not found for id: " + studyProgramSubjectId));
        if(!studyProgramSubject.getStudyProgram().getId().equals(schoolClass.getStudyProgram().getId())){
            throw new IllegalStateException("Study program differs between school class and study program subject");
        }
        TeacherSubject teacherSubject = teacherSubjectRepository.findByTeacherAndSchoolClassAndStudyProgramSubject(teacher,schoolClass,studyProgramSubject)
                .orElseThrow(()->new EntityNotFoundException("Teacher subject not found"));
        Faculty faculty  = facultyRepository.getFacultyForStudyProgramSubject(studyProgramSubject.getId());
        Long facultyId = faculty.getId();
        Season season;
        if(seasonId == null){
            season = seasonRepository.findByFaculty_IdAndIsDeletedFalseAndStatus(facultyId, SeasonStatus.ACTIVE)
                    .orElseThrow(() -> new EntityNotFoundException("Season not found"));
        }
        else{
            season = seasonRepository.findById(seasonId)
                    .orElseThrow(() -> new EntityNotFoundException("Season not found for the given id: " + seasonId));
        }
        Schedule schedule = new Schedule(
                teacherSubject,
                request.getDayOfWeek(),
                request.getStartTime(),
                request.getEndTime(),
                classroom,
                request.getSessionType(),
                season
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return savedSchedule.getId();
    }


    public ScheduleForTeacherResponse getTeacherScheduleForTeacher(User loggedUser){
        Long userId = loggedUser.getId();
        Teacher teacher = teacherRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found for user id: " + userId));
        return getTeachersSchedule(teacher.getId());
    }

    public ScheduleForTeacherResponse getTeacherScheduleForAdmin(User loggedUser,Long teacherId){
        Long userId = loggedUser.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()->new EntityNotFoundException("School admin not found for user id: " + userId));
        School school = schoolAdmin.getSchool();
        Teacher teacher = teacherRepository.findByIdAndIsDeletedFalse(teacherId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found for user id: " + teacherId));
        if(!teacher.getSchool().getId().equals(school.getId())){
            throw new AccessDeniedException("You do not have access to this teacher");
        }
        return getTeachersSchedule(teacherId);
    }

    public ScheduleForTeacherResponse getTeachersSchedule(Long teacherId){
        List<TeacherScheduleModel> scheduleModels = scheduleRepository.getTeacherSchedule(teacherId);
        Map<DayOfWeek,List<TeacherPerDayScheduleModel>> scheduleMap = new EnumMap<>(DayOfWeek.class);
        for(DayOfWeek day : DayOfWeek.values()){
            scheduleMap.put(day,new ArrayList<>());
        }
        for(TeacherScheduleModel model : scheduleModels){
            TeacherPerDayScheduleModel scheduleModel = new TeacherPerDayScheduleModel(
                    model.getStudyProgramName(),
                    model.getSubjectName(),
                    model.getSessionType(),
                    model.getYearLevel(),
                    model.getSchoolClassName(),
                    model.getClassroomNumber()
            );

            scheduleMap.get(model.getDayOfWeek()).add(scheduleModel);
        }
        return new ScheduleForTeacherResponse(
                scheduleMap
        );
    }


    public ScheduleForClassResponse getScheduleForClass(User loggedUser, Long schoolClassId){
        SchoolClass schoolClass = schoolClassRepository.findByIdAndIsDeletedFalse(schoolClassId)
                .orElseThrow(()-> new EntityNotFoundException("School class not found for id: " + schoolClassId));
        List<ScheduleForClassModel> schedule = scheduleRepository.getScheduleForSchoolClass(schoolClassId);
        Map<DayOfWeek, List<SchedulePerDayModel>> scheduleMap = new EnumMap<>(DayOfWeek.class);
        for(DayOfWeek day : DayOfWeek.values()){
            scheduleMap.put(day,new ArrayList<>());
        }
        for(ScheduleForClassModel model : schedule) {
            SchedulePerDayModel schedulePerDayModel = new SchedulePerDayModel(
                    model.getStudySubjectName(),
                    model.getType(),
                    model.getTeacherFullName(),
                    model.getClassroomNumber(),
                    model.getStartTime(),
                    model.getEndTime()
            );
            scheduleMap.get(model.getDayOfWeek()).add(schedulePerDayModel);
        }
        return new ScheduleForClassResponse(scheduleMap);
    }
}
