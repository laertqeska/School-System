package com.example.School_System.services;

import com.example.School_System.dto.mappers.TeacherMapper;
import com.example.School_System.dto.teacher.PaginatedTeacherResponse;
import com.example.School_System.dto.teacher.TeacherDetailsResponse;
import com.example.School_System.dto.teacher.TeacherModel;
import com.example.School_System.dto.teacher.UpdateTeacherRequest;
import com.example.School_System.entities.*;
import com.example.School_System.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final StudyProgramSubjectRepository studyProgramSubjectRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final UserRepository userRepository;
    private final SchoolContextService schoolContextService;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository, SchoolClassRepository schoolClassRepository, SchoolAdminRepository schoolAdminRepository, StudyProgramSubjectRepository studyProgramSubjectRepository, TeacherSubjectRepository teacherSubjectRepository, UserRepository userRepository, SchoolContextService schoolContextService) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.studyProgramSubjectRepository = studyProgramSubjectRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.userRepository = userRepository;
        this.schoolContextService = schoolContextService;
    }

    public PaginatedTeacherResponse listTeachers(User loggedUser, int page, int perPage, String search){
        Long userId = loggedUser.getId();
        SchoolAdmin schoolAdmin = schoolAdminRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("School Admin not found for user id : " + userId));
        Long schoolId = schoolAdmin.getSchool().getId();
        if(page > 0) page--;
        Pageable pageable = PageRequest.of(page,perPage);
        if(search == null || search.isBlank()){
            search = "";
        }
        else search = search.toLowerCase();

        Page<TeacherModel> teacherModelsPage = teacherRepository.findTeachersWithSearch(search,schoolId,pageable);

        return new PaginatedTeacherResponse(
                teacherModelsPage.getContent(),
                page + 1,
                perPage,
                teacherModelsPage.getTotalElements(),
                teacherModelsPage.getTotalPages()
        );
    }

    public PaginatedTeacherResponse getTeachersByFaculty(User loggedUser, int page, int perPage,String search){
        Faculty faculty = loggedUser.getFacultyOfDean();
        Long facultyId = faculty.getId();
        Long schoolId = faculty.getSchool().getId();
        Pageable pageable = PageRequest.of(page,perPage);
        Page<TeacherModel> teacherPage = teacherRepository.findTeachersWithSearchByFaculty(search,schoolId,facultyId,pageable);
        return new PaginatedTeacherResponse(
                teacherPage.getContent(),
                page,
                perPage,
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages()
        );

    }

    public TeacherDetailsResponse getTeacherDetails(Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        User user = teacher.getUser();
        List<String> studyProgramSubjects = teacherSubjectRepository.getTeacherSubjectNamesForTeacher(teacherId);
        List<String> classes = teacherSubjectRepository.getClassesNamesForTeacher(teacherId);

        return new TeacherDetailsResponse(
                user.getFirstName(),
                user.getLastName(),
                teacher.getSchool().getName(),
                teacher.getDepartment().getName(),
                user.getEmail(),
                teacher.getEmployeeId(),
                teacher.getAcademicTitle().toString(),
                teacher.getQualification(),
                studyProgramSubjects,
                classes
        );
    }

    public void updateTeacher(UpdateTeacherRequest request,Long teacherId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        TeacherMapper.updateTeacherFromRequest(teacher,request);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void deleteTeacher(Long teacherId,User loggedUser){
        Teacher teacherToDelete = teacherRepository.findById(teacherId).orElseThrow(()-> new EntityNotFoundException("Teacher with ID: " + teacherId + " does not exist!!!"));
        if (teacherToDelete.getDeleted()) {
            throw new IllegalStateException("Teacher already deleted");
        }
        School school = schoolContextService.resolveSchool(loggedUser);
        if(!school.getId().equals(teacherToDelete.getSchool().getId())){
            throw new AccessDeniedException("You do not have permission to delete this teacher!");
        }
        teacherToDelete.delete(loggedUser);
        teacherRepository.save(teacherToDelete);
        User user = teacherToDelete.getUser();
        user.delete(loggedUser);
        userRepository.save(user);
    }



}
