package com.example.School_System.services;

import com.example.School_System.dto.classroom.*;
import com.example.School_System.entities.*;
import com.example.School_System.entities.valueObjects.ClassroomType;
import com.example.School_System.entities.valueObjects.SeasonStatus;
import com.example.School_System.repositories.BuildingRepository;
import com.example.School_System.repositories.ClassroomRepository;
import com.example.School_System.repositories.SeasonRepository;
import com.example.School_System.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final BuildingRepository buildingRepository;
    private final TeacherRepository teacherRepository;
    private final SeasonRepository seasonRepository;

    public ClassroomService(ClassroomRepository classroomRepository, BuildingRepository buildingRepository, TeacherRepository teacherRepository, SeasonRepository seasonRepository) {
        this.classroomRepository = classroomRepository;
        this.buildingRepository = buildingRepository;
        this.teacherRepository = teacherRepository;
        this.seasonRepository = seasonRepository;
    }


    @Transactional
    public Long createClassroom(User loggedUser, CreateClassroomRequest request) {
        Long buildingId = request.getBuildingId();
        Building building = buildingRepository.findByIdAndIsDeletedFalse(buildingId)
                .orElseThrow(() -> new EntityNotFoundException("Building not found for id: " + buildingId));
        SchoolAdmin schoolAdmin = loggedUser.getSchoolAdmin();
        School school = schoolAdmin.getSchool();
        if (!building.getFaculty().getSchool().getId().equals(school.getId())) {
            throw new AccessDeniedException("You do not have access to this building");
        }
        boolean hasProjector = request.getHasProjector() != null ? request.getHasProjector() : false;
        boolean hasAudioSystem = request.getHasAudioSystem() != null ? request.getHasAudioSystem() : false;
        boolean isAccessible = request.getAccessible() != null ? request.getAccessible() : true;
        Classroom classroom = new Classroom(
                building,
                request.getClassroomNumber(),
                request.getClassroomName(),
                request.getClassroomType(),
                request.getCapacity(),
                hasProjector,
                hasAudioSystem,
                isAccessible
        );

        Classroom createdClassroom = classroomRepository.save(classroom);
        return createdClassroom.getId();
    }

    public PaginatedClassroomsForBuildingResponse getClassroomsForBuilding(User loggedUser, Long buildingId, int page, int perPage, String search, ClassroomType classroomType) {
        if (page > 0) {
            page--;
        }
        SchoolAdmin schoolAdmin = loggedUser.getSchoolAdmin();
        School school = schoolAdmin.getSchool();
        Building building = buildingRepository.findByIdAndIsDeletedFalse(buildingId)
                .orElseThrow(() -> new EntityNotFoundException("Building not found for id: " + buildingId));
        if (!building.getFaculty().getSchool().getId().equals(school.getId())) {
            throw new AccessDeniedException("You do not have access to this building");
        }
        if (search == null || search.isBlank()) {
            search = "";
        } else search = search.toLowerCase();
        Pageable pageable = PageRequest.of(page, perPage);
        Page<ClassroomForBuildingModel> classroomPage = classroomRepository.getPaginatedClassroomsForBuilding(pageable, buildingId, search, classroomType);
        return new PaginatedClassroomsForBuildingResponse(
                classroomPage.getContent(),
                page,
                perPage,
                classroomPage.getTotalElements(),
                classroomPage.getTotalPages()
        );
    }

    public ClassroomDetailsResponse getClassroomDetails(User loggedUser, Long classroomId) {
        SchoolAdmin schoolAdmin = loggedUser.getSchoolAdmin();
        School school = schoolAdmin.getSchool();
        Classroom classroom = classroomRepository.findByIdAndIsDeletedFalse(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found for id: " + classroomId));
        Building building = classroom.getBuilding();
        if (!building.getFaculty().getSchool().getId().equals(school.getId())) {
            throw new AccessDeniedException("You do not have access to this classroom");
        }

        return new ClassroomDetailsResponse(
                building.getBuildingName(),
                classroom.getClassroomNumber(),
                classroom.getClassroomName(),
                classroom.getClassroomType(),
                classroom.getCapacity(),
                classroom.getHasProjector(),
                classroom.getHasAudioSystem(),
                classroom.getAccessible()
        );
    }

    public void deleteClassroom(User loggedUser, Long classroomId) {
        SchoolAdmin schoolAdmin = loggedUser.getSchoolAdmin();
        School school = schoolAdmin.getSchool();
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new EntityNotFoundException("Classroom not found for id: " + classroomId));
        if (classroom.getDeleted()) {
            throw new IllegalStateException("Classroom is already deleted");
        }
        if (!classroom.getBuilding().getFaculty().getSchool().getId().equals(school.getId())) {
            throw new AccessDeniedException("You do not have access to this classroom");
        }
        classroom.delete(loggedUser);
        classroomRepository.save(classroom);
    }

    public PaginatedAvailableClassroomsResponse getAvailableClassroomsInFacultyForTeacher(User loggedUser,Long buildingId,Boolean isAccessible,Boolean hasProjector,String search, ClassroomType classroomType,LocalTime startTime, LocalTime endTime, LocalDate date, int page,int perPage){
        Long userId = loggedUser.getId();
        Teacher teacher = teacherRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher not found for user id: " + userId));
        Faculty faculty = teacher.getDepartment().getFaculty();
        return getAvailableClassroomsInFaculty(faculty.getId(),buildingId,isAccessible,hasProjector,search,classroomType,startTime,endTime,date,page,perPage);
    }


    public PaginatedAvailableClassroomsResponse getAvailableClassroomsInFaculty(Long facultyId, Long buildingId, Boolean isAccessible, Boolean hasProjector, String search, ClassroomType classroomType, LocalTime startTime, LocalTime endTime, LocalDate date, int page, int perPage) {
        if (page < 1) throw new IllegalArgumentException("Page cannot be smaller than 1");
        else {
            page--;
        }
        if (search == null || search.isBlank()) {
            search = "";
        } else {
            search = search.toLowerCase();
        }

        LocalDate searchDate = date != null ? date : LocalDate.now();
        Season currentSeason = validateDateAndTime(searchDate,startTime,endTime,facultyId);

        Pageable pageable = PageRequest.of(page, perPage);
        DayOfWeek day = searchDate.getDayOfWeek();
        Page<AvailableClassroomModel> availableClassroomsPage = classroomRepository.getPaginatedAvailableClassrooms(pageable,facultyId,day,startTime, endTime, currentSeason.getId(), buildingId,search,hasProjector,isAccessible,classroomType);
        return new PaginatedAvailableClassroomsResponse(
                availableClassroomsPage.getContent(),
                page + 1,
                perPage,
                availableClassroomsPage.getTotalElements(),
                availableClassroomsPage.getTotalPages()
        );
    }

    private Season validateDateAndTime(LocalDate date, LocalTime startTime, LocalTime endTime,Long facultyId){
        Season season = seasonRepository.findByFaculty_IdAndIsDeletedFalseAndStatus(facultyId, SeasonStatus.ACTIVE)
                .orElseThrow(()-> new EntityNotFoundException("Season not found"));
        if(date.isBefore(season.getStartDate()) || date.isAfter(season.getEndDate())){
            throw new IllegalArgumentException("Provided date is not a valid date from the current season");
        }

        if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Cannot search for classrooms in the past");
        }

        if(date.equals(LocalDate.now()) && endTime.isBefore(LocalTime.now())){
            throw new IllegalArgumentException("Cannot search for classrooms in the past");
        }

        return season;
    }
}